package com.github.zhuyiyi1990.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DemoReceive {

    @RabbitListener(queues = "myQueue")
    public void demo(String msg) {
        System.out.println("获取到的消息1111：" + msg);
    }

    @RabbitListener(queues = "myQueue")
    public void demo2(String msg) {
        System.out.println("获取到的消息2222：" + msg);
    }

    @RabbitListener(queues = "myfanout1")
    public void demo3(String msg) {
        System.out.println("fanout1:" + msg);
    }

    @RabbitListener(queues = "myfanout2")
    public void demo4(String msg) {
        System.out.println("fanout2:" + msg);
    }

    @RabbitListener(queues = "topic1")
    public void demo5(String msg) {
        System.out.println("topic1:" + msg);
    }

    @RabbitListener(queues = "topic2")
    public void demo6(String msg) {
        System.out.println("topic2:" + msg);
    }

}