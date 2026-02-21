package com.github.zhuyiyi1990;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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

    @Test
    public void testAck() {
        /*// 正确的交换机和正确的队列
        rabbitTemplate.convertAndSend("amq.direct", "my-direct-queue", "Hello Ack");*/
        /*// 错误的交换机
        rabbitTemplate.convertAndSend("amq.direct~", "my-direct-queue", "Hello Ack");*/
        // 正确的交换机和错误的队列
        rabbitTemplate.convertAndSend("amq.direct", "my-direct-queue~", "Hello Ack");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
        }
    }

    @Test
    public void testSendMessageTTL() {
        // 1、创建消息后置处理器对象
        MessagePostProcessor messagePostProcessor = (Message message) -> {

            // 设定 TTL 时间，以毫秒为单位
            message.getMessageProperties().setExpiration("15000");

            return message;
        };

        // 2、发送消息
        // 规则：过期检查的时机。RabbitMQ只会检查队列头部的消息是否过期。这意味着，如果队列头部的消息未过期，即使排在后面的消息已经过了它的TTL时间，也不会被立即移除，必须等待前面的消息被消费或过期后，才会被检查和处理
        rabbitTemplate.convertAndSend(
                "my.timeout",
                "my-timeout-queue",
                "Hello timeout 1", messagePostProcessor);
        rabbitTemplate.convertAndSend(
                "my.timeout",
                "my-timeout-queue",
                "Hello timeout 2");
    }

}