package com.crazy.ithomecrawler.redis;

import com.crazy.ithomecrawler.domain.HotComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param key
     * @param hotComment
     * 添加热评至 redis
     */
    public void rankAdd(String key, HotComment hotComment){
        ZSetOperations<String,HotComment> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key,hotComment,hotComment.getUp());
    }

    /**
     *
     * @param key
     * @param top 前top条记录
     * @return
     */
    public Set<HotComment> rankGet(String key,int top){
        ZSetOperations<String,HotComment> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.range(key,0,top);
    }

    /**
     *
     * @param key
     * @param values
     * @desc 保存最近一次抓取的位置点
     */
    public void listAdd(String key,List<String> values){
        ListOperations<String,String> listOperations = redisTemplate.opsForList();
        listOperations.rightPushAll(key,values);
    }

    public void listRemove(String key){
        redisTemplate.delete(key);
    }

    /**
     *
     * @param key
     * @param values
     * @return
     * @desc 判断是否抓取结束位置
     */
    public boolean containsValue(String key,List<String> values){
        ListOperations<String,String> listOperations = redisTemplate.opsForList();
        List<String> list = listOperations.range(key,0,-1);
        for (String val : values){
            if(list.contains(val)){
                return true;
            }
        }
        return false;
    }
}
