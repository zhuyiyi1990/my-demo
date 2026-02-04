package com.github.zhuyiyi1990;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SpringBootTest
class MyRedisDemoApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
    }

    // 数据类型：String
    @Test
    void testString() {
        final String key = "helloString";
        stringRedisTemplate.opsForValue().set(key, "world");
        String helloString = stringRedisTemplate.opsForValue().get(key);
        System.out.println("helloString = " + helloString);
    }

    // 数据类型：Hash
    @Test
    void testHash() {
        final String key = "helloHash";
        stringRedisTemplate.opsForHash().put(key, "hello1", "world1");
        stringRedisTemplate.opsForHash().put(key, "hello2", "world2");
        stringRedisTemplate.opsForHash().put(key, "hello3", "world3");
        Map<Object, Object> helloHash = stringRedisTemplate.opsForHash().entries(key);
        System.out.println(helloHash);
    }

    // 数据类型：List
    @Test
    void testList() {
        // 5 3 1 2 4
        final String key = "helloList";
        stringRedisTemplate.delete(key);
        prepareListData(key);
        while (true) {
            String hello = stringRedisTemplate.opsForList().leftPop(key);
            if (StringUtils.isBlank(hello)) {
                break;
            }
            System.out.println(hello);
        }
        prepareListData(key);
        Long size = stringRedisTemplate.opsForList().size(key);
        List<String> helloList = stringRedisTemplate.opsForList().range(key, 0, size - 1);
        System.out.println("helloList = " + helloList);
    }

    private void prepareListData(final String key) {
        stringRedisTemplate.opsForList().leftPush(key, "1");
        stringRedisTemplate.opsForList().rightPush(key, "2");
        stringRedisTemplate.opsForList().leftPush(key, "3");
        stringRedisTemplate.opsForList().rightPush(key, "4");
        stringRedisTemplate.opsForList().leftPush(key, "5");
    }

    // 数据类型：Set
    @Test
    void testSet() {
        final String key = "helloSet";
        stringRedisTemplate.opsForSet().add(key, "hello1", "hello2", "hello3");
        stringRedisTemplate.opsForSet().add(key, "hello1", "hello2", "hello3");
        Set<String> helloSet = stringRedisTemplate.opsForSet().members(key);
        System.out.println("helloSet = " + helloSet);
    }

    // 数据类型：Sorted set
    @Test
    void testSortedSet() {
        final String key = "helloSortedSet";
        stringRedisTemplate.delete(key);
        prepareSortedSetData(key);
        while (true) {
            ZSetOperations.TypedTuple<String> hello = stringRedisTemplate.opsForZSet().popMax(key);
            if (Objects.isNull(hello)) {
                break;
            }
            System.out.println(hello.getScore() + "->" + hello.getValue());
        }
        prepareSortedSetData(key);
        Long size = stringRedisTemplate.opsForZSet().size(key);
        Set<String> set = stringRedisTemplate.opsForZSet().range(key, 0, size - 1);
        System.out.println(set);
    }

    private void prepareSortedSetData(final String key) {
        stringRedisTemplate.opsForZSet().add(key, "hello3", 1);
        stringRedisTemplate.opsForZSet().add(key, "hello4", 2);
        stringRedisTemplate.opsForZSet().add(key, "hello2", 3);
        stringRedisTemplate.opsForZSet().add(key, "hello5", 4);
        stringRedisTemplate.opsForZSet().add(key, "hello1", 5);
    }

    // 数据类型：Bitmap
    @Test
    void testBitmap() {
        // TODO
    }

    @Test
    void testTransaction() {
        // TODO
    }

}