package com.github.zhuyiyi1990.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 配置类放在消费者端（多实例不会多次创建）
@Configuration
public class RabbitmqConfig {

    @Bean
    protected Queue myDirectQueue() {
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
    public DirectExchange myDirectExchange() {
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

}