package com.github.zhuyiyi1990;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = MyRabbitMqDemoPublisherTransactionApplication.class)
@Slf4j
public class MyRabbitMqDemoPublisherTransactionApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
     * 测试结果不佳
     * 由于junit里如果加了@Transactional会自动回滚，所以需要加上@Rollback(false)注解
     * 但是加了@Rollback又无法测试事务的原子性
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testSendMessageInTx() {
        // 1、发送第一条消息
        rabbitTemplate.convertAndSend("my.transaction", "my-transaction-queue", "I am a dragon(tx msg ~~~01)");

        // 2、抛出异常
        log.info("do bad:" + 10 / 0);

        // 3、发送第二条消息
        rabbitTemplate.convertAndSend("my.transaction", "my-transaction-queue", "I am a dragon(tx msg ~~~02)");
    }

}