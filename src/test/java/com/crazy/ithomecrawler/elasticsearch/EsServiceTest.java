package com.crazy.ithomecrawler.elasticsearch;

import com.crazy.ithomecrawler.domain.HotComment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EsServiceTest {

    @Autowired
    private EsService esService;

    @Test
    public void findByUser() throws Exception {
//        HotComment hotComment = new HotComment();
//        hotComment.setId(111L);
//        hotComment.setCommentId("123456");
//        hotComment.setArticleUrl("https://www.baidu.com");
//        hotComment.setUser("Sisu");
//
//        esService.addHotComment(hotComment);
        List<HotComment>  comments = esService.findByUser("Sisu");
        for (HotComment comment : comments){
            System.out.println(comment.toString());
        }
    }

}