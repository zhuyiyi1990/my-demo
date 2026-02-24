package com.github.zhuyiyi1990.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    private BigDecimal amount;

    private String sex;

    private String email;

}