package com.github.zhuyiyi1990;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zhuyiyi1990.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class MyRedisCustomDemoApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    // 测试两个RedisTemplate是否是同一个对象
    @Test
    void testIfTwoRedisTemplateIsOne() {
        System.out.println(Objects.equals(redisTemplate, stringRedisTemplate));
    }

    // 把对象保存为String类型
    @Test
    void testSaveObjectAsString() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user:1", new User("1", "朱一一", 18, new BigDecimal("5.43")));
        User user = (User) valueOperations.get("user:1");
        System.out.println("user = " + user);
    }

    // 把对象保存为Hash类型
    @Test
    void testSaveObjectAsHash() {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        User user = new User("2", "朱一一", 18, new BigDecimal("5.43"));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(user, Map.class);
        hashOperations.putAll("user:2", map);
        Map<Object, Object> userMap = hashOperations.entries("user:2");
        user = mapper.convertValue(userMap, User.class);
        System.out.println("user = " + user);
    }

}