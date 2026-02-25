package com.github.zhuyiyi1990.service.impl;

import com.github.zhuyiyi1990.pojo.Product;
import com.github.zhuyiyi1990.service.ProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // 多参数缓存，使用SpEL表达式构建key
    @Override
    @Cacheable(value = "products", key = "#category + '_' + #page + '_' + #size")
    public List<Product> getProductsByCategory(String category, int page, int size) {
        System.out.println("查询数据库，分类：" + category + ", 页码：" + page);
        // 模拟查询逻辑
        return Arrays.asList(
                new Product(1L, "商品1", category, 5.43, null),
                new Product(2L, "商品2", category, 5.43, null)
        );
    }

    // 使用方法名作为key的一部分
    @Override
    @Cacheable(value = "products", key = "#root.methodName + '_' + #category")
    public List<Product> getHotProducts(String category) {
        System.out.println("查询热门商品，分类：" + category);
        // 模拟查询逻辑
        return Arrays.asList(
                new Product(1L, "热门商品1", category, 5.43, null),
                new Product(2L, "热门商品2", category, 5.43, null)
        );
    }

}