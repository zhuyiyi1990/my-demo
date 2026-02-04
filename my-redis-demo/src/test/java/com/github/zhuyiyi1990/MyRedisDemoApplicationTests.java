package com.github.zhuyiyi1990;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class MyRedisDemoApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void setString() {
        stringRedisTemplate.opsForValue().set("hello", "world");
    }

    @Test
    void getString() {
        String hello = stringRedisTemplate.opsForValue().get("hello");
        System.out.println("hello = " + hello);
    }

}