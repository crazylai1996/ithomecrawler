package com.crazy.ithomecrawler.controller;

import com.crazy.ithomecrawler.domain.HotComment;
import com.crazy.ithomecrawler.elasticsearch.EsService;
import com.crazy.ithomecrawler.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

/**
 * @Author: Sisu
 * @Description:
 * @Date: Created in 11:57 2017-08-25 0025
 * @Modified By:
 **/
@Controller
@RequestMapping("/ithome")
public class HotCommentController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private EsService esService;

    /**
     * 首页
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("search");
        Set<HotComment> set = redisService.rankGet("ithome:hotrank",50);
        mav.addObject("comments",set);
        return mav;
    }

    /**
     * 搜索
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ModelAndView search(@PathVariable("keyword") String keyword){
        ModelAndView mav = new ModelAndView("search");
        List<HotComment> list = esService.findByUser(keyword);
        mav.addObject("comments",list);
        return mav;
    }
}
