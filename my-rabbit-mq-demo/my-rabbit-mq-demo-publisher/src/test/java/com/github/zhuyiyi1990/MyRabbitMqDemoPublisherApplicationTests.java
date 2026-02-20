package com.github.zhuyiyi1990;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MyRabbitMqDemoPublisherApplication.class)
public class MyRabbitMqDemoPublisherApplicationTests {

    /*@Autowired
    private AmqpTemplate amqpTemplate;*/

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testMethod1() {
        rabbitTemplate.convertAndSend("my-direct-queue", "这是发送的内容");
        System.out.println("发送成功！");
    }

    @Test
    public void testMethod2() {
        rabbitTemplate.convertAndSend("amq.fanout", null, "fanout msg");
        System.out.println("发送成功！");
    }

    @Test
    public void testMethod3() {
        // rabbitTemplate.convertAndSend("amq.topic", "com.github.zhuyiyi1990.a", "topic msg");
        rabbitTemplate.convertAndSend("amq.topic", "com.github.zhuyiyi1990.abc", "topic msg");
        System.out.println("发送成功！");
    }

}