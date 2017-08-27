package com.crazy.ithomecrawler.elasticsearch;

import com.crazy.ithomecrawler.domain.HotComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsService {
    @Autowired
    private EsRepository esRepository;

    public void addHotComment(HotComment hotComment){
        esRepository.save(hotComment);
    }

    /**
     * 缓存搜索结果
     * @param user
     * @return
     */
    @Cacheable(value = "ithome:hotcomments", key = "'ithome:user:'+#user")
    public List<HotComment> findByUser(String user){
        return esRepository.findByUser(user);
    }
}
