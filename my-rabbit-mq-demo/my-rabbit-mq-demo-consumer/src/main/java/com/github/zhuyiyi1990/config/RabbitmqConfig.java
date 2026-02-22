package com.github.zhuyiyi1990.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

// 配置类放在消费者端（多实例不会多次创建）
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue myDirectQueue() {
        // return new Queue("my-direct-queue", true, false, false);
        return new Queue("my-direct-queue");
    }

    @Bean
    public Queue myFanoutQueue1() {
        return new Queue("my-fanout-queue-1");
    }

    @Bean
    public Queue myFanoutQueue2() {
        return new Queue("my-fanout-queue-2");
    }

    @Bean
    public Queue myTopicQueue1() {
        return new Queue("my-topic-queue-1");
    }

    @Bean
    public Queue myTopicQueue2() {
        return new Queue("my-topic-queue-2");
    }

    @Bean
    public Queue myTimeoutQueue() {
        Queue queue = new Queue("my-timeout-queue");
        queue.addArgument("x-message-ttl", 30000);
        return queue;
    }

    @Bean
    public Queue myDelayedQueue() {
        return new Queue("my-delayed-queue");
    }

    @Bean
    public Queue myTransactionQueue() {
        return new Queue("my-transaction-queue");
    }

    @Bean
    public Queue myPriorityQueue() {
        Queue queue = new Queue("my-priority-queue");
        queue.addArgument("x-max-priority", 10);
        return queue;
    }

    @Bean
    public DirectExchange myDirectExchange() {
        // return new DirectExchange("amq.direct", true, false);
        return new DirectExchange("amq.direct");
    }

    @Bean
    public FanoutExchange myFanoutExchange() {
        return new FanoutExchange("amq.fanout");
    }

    @Bean
    public TopicExchange myTopicExchange() {
        return new TopicExchange("amq.topic");
    }

    @Bean
    public DirectExchange myTimeoutExchange() {
        return new DirectExchange("my.timeout");
    }

    @Bean
    public CustomExchange myDelayedExchange() {
        Map<String, Object> args = new HashMap<>();
        // 关键配置：指定交换机类型为延迟插件支持的类型，并开启延迟功能
        args.put("x-delayed-type", "direct");
        /*
         * 参数说明：
         * 1. 交换机名称
         * 2. 交换机类型，对于延迟插件，固定为 "x-delayed-message"
         * 3. 是否持久化
         * 4. 是否自动删除
         * 5. 其他参数，这里传入包含 "x-delayed-type" 的Map
         */
        return new CustomExchange("my.delayed",
                "x-delayed-message",
                true,
                false,
                args);
    }

    @Bean
    public DirectExchange myTransactionExchange() {
        return new DirectExchange("my.transaction");
    }

    @Bean
    public DirectExchange myPriorityExchange() {
        return new DirectExchange("my.priority");
    }

    @Bean
    public Binding directBinding(Queue myDirectQueue, DirectExchange myDirectExchange) {
        return BindingBuilder.bind(myDirectQueue).to(myDirectExchange).with("my-direct-queue");
    }

    @Bean
    public Binding fanoutBinding(Queue myFanoutQueue1, FanoutExchange myFanoutExchange) {
        return BindingBuilder.bind(myFanoutQueue1).to(myFanoutExchange);
    }

    @Bean
    public Binding fanoutBinding2(Queue myFanoutQueue2, FanoutExchange myFanoutExchange) {
        return BindingBuilder.bind(myFanoutQueue2).to(myFanoutExchange);
    }

    @Bean
    public Binding topicBinding(Queue myTopicQueue1, TopicExchange myTopicExchange) {
        return BindingBuilder.bind(myTopicQueue1).to(myTopicExchange).with("com.github.zhuyiyi1990.*");
    }

    @Bean
    public Binding topicBinding2(Queue myTopicQueue2, TopicExchange myTopicExchange) {
        // return BindingBuilder.bind(myTopicQueue2).to(myTopicExchange).with("com.github.zhuyiyi1990.a");
        return BindingBuilder.bind(myTopicQueue2).to(myTopicExchange).with("com.github.zhuyiyi1990.#");
    }

    @Bean
    public Binding timeoutBinding(Queue myTimeoutQueue, DirectExchange myTimeoutExchange) {
        return BindingBuilder.bind(myTimeoutQueue).to(myTimeoutExchange).with("my-timeout-queue");
    }

    @Bean
    public Binding delayedBinding(Queue myDelayedQueue, CustomExchange myDelayedExchange) {
        // 将队列绑定到延迟交换机
        return BindingBuilder.bind(myDelayedQueue)
                .to(myDelayedExchange)
                .with("my-delayed-queue")
                .noargs();
    }

    @Bean
    public Binding transactionBinding(Queue myTransactionQueue, DirectExchange myTransactionExchange) {
        return BindingBuilder.bind(myTransactionQueue).to(myTransactionExchange).with("my-transaction-queue");
    }

    @Bean
    public Binding priorityBinding(Queue myPriorityQueue, DirectExchange myPriorityExchange) {
        return BindingBuilder.bind(myPriorityQueue).to(myPriorityExchange).with("my-priority-queue");
    }

}