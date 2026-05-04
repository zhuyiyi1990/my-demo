package com.github.zhuyiyi1990;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MyRocketMqDemoProducerApplication.class)
public class MyRocketMqDemoProducerApplicationTests {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void test() {
        String msg = "测试消息";
        // 参数1：主题Topic名称，参数2：消息内容
        rocketMQTemplate.convertAndSend("my-spring-boot-topic", msg);
        System.out.println("消息已发送: " + msg);
    }

}