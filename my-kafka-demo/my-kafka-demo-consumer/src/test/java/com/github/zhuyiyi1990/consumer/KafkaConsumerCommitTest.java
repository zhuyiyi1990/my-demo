package com.github.zhuyiyi1990.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KafkaConsumerCommitTest {

    public static void main(String[] args) {
        // 配置属性集合
        Map<String, Object> configMap = new HashMap<>();
        // 配置属性：Kafka集群地址
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.58.100:9094");
        // 配置属性: Kafka传输的数据为KV对，所以需要对获取的数据分别进行反序列化
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 配置属性: 消费者组
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, "atguigu");
        // 配置属性: 读取数据的位置 ，取值为earliest（最早），latest（最晚）
        configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 配置属性: 自动提交偏移量
        configMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        // 创建消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configMap);
        // 消费者订阅指定主题的数据
        consumer.subscribe(Collections.singletonList("test"));

        // 设置的是从头开始读，但是如果需要从中间开始读，需要这段代码
        boolean flg = true;
        while (flg) {
            consumer.poll(Duration.ofMillis(100));
            final Set<TopicPartition> assignment = consumer.assignment();
            if (assignment != null && !assignment.isEmpty()) {
                for (TopicPartition topicPartition : assignment) {
                    if ("test".equals(topicPartition.topic())) {
                        // 比如从偏移量2开始读
                        consumer.seek(topicPartition, 2);
                        flg = false;
                    }
                }
            }
        }

        while (true) {
            // 每隔100毫秒，抓取一次数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            // 打印抓取的数据
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("K = " + record.key() + ", V = " + record.value());
            }
            // 同步提交
            consumer.commitSync();
            // 异步提交
            consumer.commitAsync();
        }
    }

}