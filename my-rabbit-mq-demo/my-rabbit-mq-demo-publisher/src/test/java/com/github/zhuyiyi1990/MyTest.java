package com.github.zhuyiyi1990;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PublisherApplication.class)
public class MyTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testMethod1() {
        amqpTemplate.convertAndSend("myQueue", "这是发送的内容");
        System.out.println("发送成功！");
    }

    @Test
    public void testMethod2() {
        amqpTemplate.convertAndSend("amq.fanout", "core", "fanout msg");
        System.out.println("发送成功！");
    }

    @Test
    public void testMethod3() {
//        amqpTemplate.convertAndSend("amq.topic", "com.github.zhuyiyi1990.a", "topic msg");
        amqpTemplate.convertAndSend("amq.topic", "com.github.zhuyiyi1990.abc", "topic msg");
        System.out.println("发送成功！");
    }

}