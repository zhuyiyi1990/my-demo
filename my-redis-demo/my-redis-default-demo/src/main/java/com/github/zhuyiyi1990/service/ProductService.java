package com.github.zhuyiyi1990.service;

import com.github.zhuyiyi1990.pojo.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByCategory(String category, int page, int size);

    List<Product> getHotProducts(String category);

}