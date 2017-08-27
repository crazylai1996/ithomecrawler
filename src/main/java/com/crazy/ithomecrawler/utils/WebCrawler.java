package com.crazy.ithomecrawler.utils;

import com.crazy.ithomecrawler.domain.HotComment;
import com.crazy.ithomecrawler.elasticsearch.EsRepository;
import com.crazy.ithomecrawler.elasticsearch.EsService;
import com.crazy.ithomecrawler.mybatis.mapper.HotCommentMapper;
import com.crazy.ithomecrawler.redis.RedisService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WebCrawler {

    private static final String encoding = "utf-8";

    @Autowired
    private HotCommentMapper hotCommentMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EsService esService;

    private static boolean done = false;
    private static final int THREAD_NUM = 15;
    private static AtomicInteger page = new AtomicInteger(0);
    private static List<String> breakpoints;

    /**
     * 定时爬取更新
     */
    //@Scheduled(initialDelay = 1000, fixedRate = 1000*60*60*24*3)
    public void start(){
        done = false;
        System.out.println("开始爬取:"+System.currentTimeMillis());
        for (int i = 0;i<THREAD_NUM;++i){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!done) {
                        int p = page.incrementAndGet();
                        crawl(p);
                    }
                    System.out.println(Thread.currentThread().getName()+":结束:"+System.currentTimeMillis());
                }
            },"Thread--"+i).start();
        }
    }

    public synchronized void stop(){
        done = true;
        redisService.listRemove("ithome:breakpoints");
        redisService.listAdd("ithome:breakpoints",breakpoints);
    }

    /**
     * @param page :页码
     */
    public void crawl(int page){
        String url = "https://www.ithome.com/ithome/getajaxdata.aspx?" +
                "page="+page+"&type=indexpage&randnum="+Math.random();
        String src = getHtmlSrc(url,encoding);
        List<String> links = getArticleLinks(src);
        if (links.size()<=0){
            stop();
            return ;
        }
        //不知还有没更好的方法判断最近一次抓取的位置？
        if(redisService.containsValue("ithome:breakpoints",links)){
            stop();
            return ;
        }
        //保存第一页链接做结束点
        if (page == 1){
            breakpoints = links;
        }
        for (String link:links){
            parseAndSaveHotComments(link);
        }
    }

    /**
     *
     * @param url
     * @param encoding 编码
     * @return 网页源代码
     */
    public String getHtmlSrc(String url,String encoding){
        StringBuilder src = new StringBuilder();
        InputStreamReader isr = null;
        try {
            URL urlObj = new URL(url);//建立网络链接
            URLConnection urlConn = urlObj.openConnection();//打开链接
            isr = new InputStreamReader(urlConn.getInputStream(),encoding);//建立文件输入流
            BufferedReader reader = new BufferedReader(isr);//建立缓冲
            String line = null;
            while ((line = reader.readLine())!=null){
                src.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (isr != null){
                    isr.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return src.toString();
    }

    /**
     * @param srcCode
     * @return 解析源代码，获取文章链接
     */
    public List<String> getArticleLinks(String srcCode){
        List<String> links = new ArrayList<String>();
        Document document = Jsoup.parse(srcCode);
        Elements articleEls = document.select("h2>a");

        for (Element el:articleEls){
            String href = el.attr("href");
            links.add(href);
        }
        return links;
    }

    /**
     *
     * @param articleHref 文章链接
     * @description 使用Jsoup解析热评内容并保存
     */
    public void parseAndSaveHotComments(String articleHref){
        String articlePage = getHtmlSrc(articleHref,encoding);
        Document document = Jsoup.parse(articlePage);
        Element iframeEl = document.getElementById("ifcomment");
        if(iframeEl == null) {
            return ;
        }
        String commentHref = iframeEl.attr("src");//评论页面URL

        //获取文章ID
        document = Jsoup.parse(getHtmlSrc("http:"+commentHref,encoding));
        Element articleIdInput = document.getElementById("newsid");
        String articleId = articleIdInput.attr("value");

        //获取热评数据并解析
        String link = "http://dyn.ithome.com/ithome/getajaxdata.aspx?newsID="+articleId+"&type=hotcomment";
        String hotCommentPage = getHtmlSrc(link,encoding);
        document = Jsoup.parse(hotCommentPage);
        Elements hotCommentEls = document.select("li.entry");

        HotComment hotComment = null;
        for (Element el:hotCommentEls){
            hotComment = new HotComment();
            String  commontId = el.attr("cid");
            String user = el.select("strong.nick a").text();
            String comment = el.getElementsByTag("P").text();
            int up = getNumber(el.select("a.s").text());
            int down = getNumber(el.select("a.a").text());
            String posandtime = el.select("span.posandtime").text();
            String mobile = el.select("span.mobile a").text();

            hotComment = new HotComment();
            hotComment.setCommentId(commontId);
            hotComment.setArticleUrl(articleHref);
            hotComment.setUser(user);
            hotComment.setComment(comment);
            hotComment.setUp(up);
            hotComment.setDown(down);
            hotComment.setPosandtime(posandtime);
            hotComment.setMobile(mobile);

            hotCommentMapper.addHotComment(hotComment);//保存数据至数据库，这里保不保存其实都可以
            esService.addHotComment(hotComment);//添加索引
            if(hotComment.getUp()>=2500){
                redisService.rankAdd("ithome:hotrank",hotComment);//缓存大于2500个赞的热评
            }

            //System.out.println(hotComment.toString());
        }
    }

    /**
     *
     * @param str
     * @return 解析"()"中的数字
     */
    public int getNumber(String str){
        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Matcher matcher = pattern.matcher(str);

        if(matcher.find()){
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }

//    public static void main(String [] args){
//        new WebCrawler().start();
//    }
}
