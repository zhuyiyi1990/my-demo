package com.github.zhuyiyi1990.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends ElasticsearchRepository<Product, Long> {
}