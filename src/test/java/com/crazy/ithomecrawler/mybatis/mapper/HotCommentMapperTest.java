package com.crazy.ithomecrawler.mybatis.mapper;

import com.crazy.ithomecrawler.domain.HotComment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotCommentMapperTest {

    @Autowired
    private HotCommentMapper hotCommentMapper;

    @Test
    public void addHotComment() throws Exception {
        HotComment hotComment = new HotComment();
        hotComment.setId(111L);
        hotComment.setCommentId("123456");
        hotComment.setArticleUrl("https://www.baidu.com");
        hotComment.setUser("sisu");

        hotCommentMapper.addHotComment(hotComment);
    }

}