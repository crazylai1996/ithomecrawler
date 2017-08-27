package com.crazy.ithomecrawler.elasticsearch;

import com.crazy.ithomecrawler.domain.HotComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsRepository extends ElasticsearchRepository<HotComment,Long>{
    public List<HotComment> findByUser(String user);
}
