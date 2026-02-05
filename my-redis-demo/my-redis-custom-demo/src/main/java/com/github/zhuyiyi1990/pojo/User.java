package com.github.zhuyiyi1990.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private String id;

    private String name;

    private Integer age;

    private BigDecimal amount;

}