package com.crazy.ithomecrawler.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebCrawlerTest {

    @Autowired
    private WebCrawler webCrawler;

    @Test
    public void getHtmlSrc() throws Exception {
        System.out.println(webCrawler.getHtmlSrc("https://www.ithome.com/html/android/322635.htm","utf-8"));
    }

    @Test
    public void getArticleLinks() throws Exception {
        for(int i=0;i<100;i++){
            String url = "https://www.ithome.com/ithome/getajaxdata.aspx?" +
                    "page="+i+"&type=indexpage&randnum="+Math.random();
            String src = webCrawler.getHtmlSrc(url,"utf-8");
            List<String> list = webCrawler.getArticleLinks(src);
            for (String link:list){
                System.out.println(link);
            }
        }
//        Assert.assertNull(list);
    }

    @Test
    public void parseAndSaveHotComments() throws Exception {
        webCrawler.parseAndSaveHotComments("https://www.ithome.com/html/android/322635.htm");
    }

    @Test
    public void getNumber() throws  Exception{
        System.out.println(webCrawler.getNumber("支持(189)"));
    }
}