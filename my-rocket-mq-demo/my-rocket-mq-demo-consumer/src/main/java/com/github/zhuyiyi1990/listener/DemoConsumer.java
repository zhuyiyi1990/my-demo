package com.github.zhuyiyi1990.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
// 指定要监听的Topic、消费者组、以及Tag（*代表消费所有Tag）
@RocketMQMessageListener(topic = "my-spring-boot-topic", consumerGroup = "my-spring-boot-consumer-group", selectorExpression = "*")
public class DemoConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 这里就是处理业务逻辑的地方
        System.out.println("消费到消息啦: " + message);
    }

}