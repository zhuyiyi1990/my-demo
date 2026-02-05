package com.github.zhuyiyi1990;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SpringBootTest
class MyRedisDefaultDemoApplicationTests {

    /*
     * 默认配置的话会报错
     * No qualifying bean of type 'org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>' available: expected at least 1 bean which qualifies as autowire candidate.
     */
    /*@Autowired
    private RedisTemplate<String, Object> redisTemplate;*/

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

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

    // 数据类型：String
    @Test
    void testString() {
        final String key = "helloString";
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, "world");
        String helloString = valueOperations.get(key);
        System.out.println("helloString = " + helloString);
    }

    // 数据类型：Hash
    @Test
    void testHash() {
        final String key = "helloHash";
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, "hello1", "world1");
        hashOperations.put(key, "hello2", "world2");
        hashOperations.put(key, "hello3", "world3");
        Map<Object, Object> helloHash = hashOperations.entries(key);
        System.out.println(helloHash);
    }

    // 数据类型：List
    @Test
    void testList() {
        // 5 3 1 2 4
        final String key = "helloList";
        stringRedisTemplate.delete(key);
        prepareListData(key);
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        while (true) {
            String hello = listOperations.leftPop(key);
            if (StringUtils.isBlank(hello)) {
                break;
            }
            System.out.println(hello);
        }
        prepareListData(key);
        Long size = listOperations.size(key);
        List<String> helloList = listOperations.range(key, 0, size - 1);
        System.out.println("helloList = " + helloList);
    }

    private void prepareListData(final String key) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        listOperations.leftPush(key, "1");
        listOperations.rightPush(key, "2");
        listOperations.leftPush(key, "3");
        listOperations.rightPush(key, "4");
        listOperations.leftPush(key, "5");
    }

    // 数据类型：Set
    @Test
    void testSet() {
        final String key = "helloSet";
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        setOperations.add(key, "hello1", "hello2", "hello3");
        setOperations.add(key, "hello1", "hello2", "hello3");
        Set<String> helloSet = setOperations.members(key);
        System.out.println("helloSet = " + helloSet);
    }

    // 数据类型：Sorted set
    @Test
    void testSortedSet() {
        final String key = "helloSortedSet";
        stringRedisTemplate.delete(key);
        prepareSortedSetData(key);
        ZSetOperations<String, String> sortedSetOperations = stringRedisTemplate.opsForZSet();
        while (true) {
            ZSetOperations.TypedTuple<String> hello = sortedSetOperations.popMax(key);
            if (Objects.isNull(hello)) {
                break;
            }
            System.out.println(hello.getScore() + "->" + hello.getValue());
        }
        prepareSortedSetData(key);
        Long size = sortedSetOperations.size(key);
        Set<String> set = sortedSetOperations.range(key, 0, size - 1);
        System.out.println(set);
    }

    private void prepareSortedSetData(final String key) {
        ZSetOperations<String, String> sortedSetOperations = stringRedisTemplate.opsForZSet();
        sortedSetOperations.add(key, "hello3", 1);
        sortedSetOperations.add(key, "hello4", 2);
        sortedSetOperations.add(key, "hello2", 3);
        sortedSetOperations.add(key, "hello5", 4);
        sortedSetOperations.add(key, "hello1", 5);
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