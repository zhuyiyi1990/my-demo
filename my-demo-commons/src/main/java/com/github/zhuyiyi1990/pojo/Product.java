package com.github.zhuyiyi1990.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product implements Serializable {

    /**
     * 商品唯一标识
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 分类名称
     */
    private String category;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 图片地址
     */
    private String images;

}