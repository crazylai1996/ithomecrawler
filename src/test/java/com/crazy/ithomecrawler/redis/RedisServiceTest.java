package com.crazy.ithomecrawler.redis;

import com.crazy.ithomecrawler.domain.HotComment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @Author: Sisu
 * @Description:
 * @Date: Created in 18:32 2017-08-25 0025
 * @Modified By:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void rankAdd() throws Exception {
        HotComment hotComment = new HotComment();
        hotComment.setId(111L);
        hotComment.setCommentId("123456");
        hotComment.setArticleUrl("https://www.baidu.com");
        hotComment.setUser("sisu");

        redisService.rankAdd("hotrank",hotComment);
    }

    @Test
    public void rankGet() throws Exception {
        Set<HotComment> comments = redisService.rankGet("hotrank",1);
        for (HotComment comment: comments){
            System.out.println(comment.toString());
        }
    }

    @Test
    public void listAdd() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        redisService.listAdd("breakpoints",list);
    }

    @Test
    public void listRemove() throws Exception {
        redisService.listRemove("breakpoints");
    }

    @Test
    public void containsValue() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("aaa");

        Assert.assertTrue(redisService.containsValue("breakpoints",list));
    }

}