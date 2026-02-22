package com.github.zhuyiyi1990.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class MyRabbitListener {

    @RabbitListener(queues = "my-direct-queue")
    public void demo(String msg, Message message, Channel channel) throws Exception {
        // 1、获取当前消息的 deliveryTag 值备用
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // 2、正常业务操作
            System.out.println("获取到的消息1111：" + msg);

            // System.out.println(10 / 0);

            // 3、给 RabbitMQ 服务器返回 ACK 确认信息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

            // 4、获取信息，看当前消息是否曾经被投递过
            Boolean redelivered = message.getMessageProperties().getRedelivered();

            if (!redelivered) {
                // 5、如果没有被投递过，那就重新放回队列，重新投递，再试一次
                channel.basicNack(deliveryTag, false, true);
            } else {
                // 6、如果已经被投递过，且这一次仍然进入了 catch 块，那么返回拒绝且不再放回队列
                // channel.basicNack(deliveryTag, false, false);
                channel.basicReject(deliveryTag, false);
            }

        }
    }

    @RabbitListener(queues = "my-direct-queue")
    public void demo2(String msg, Message message, Channel channel) {
        System.out.println("获取到的消息2222：" + msg);
    }

    @RabbitListener(queues = "my-fanout-queue-1")
    public void demo3(String msg, Message message, Channel channel) {
        System.out.println("fanout1:" + msg);
    }

    @RabbitListener(queues = "my-fanout-queue-2")
    public void demo4(String msg, Message message, Channel channel) {
        System.out.println("fanout2:" + msg);
    }

    @RabbitListener(queues = "my-topic-queue-1")
    public void demo5(String msg, Message message, Channel channel) {
        System.out.println("topic1:" + msg);
    }

    @RabbitListener(queues = "my-topic-queue-2")
    public void demo6(String msg, Message message, Channel channel) {
        System.out.println("topic2:" + msg);
    }

    @RabbitListener(queues = "my-delayed-queue")
    public void demo7(String msg, Message message, Channel channel) throws Exception {
        log.info("[生产者]" + msg);
        log.info("[消费者]" + new SimpleDateFormat("hh:mm:ss").format(new Date()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "my-priority-queue")
    public void demo8(String msg, Message message, Channel channel) throws Exception {
        log.info(msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}