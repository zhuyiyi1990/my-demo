package com.github.zhuyiyi1990.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order implements Serializable {

    private Long id;

    private String orderNo;

    private Double amount;

}