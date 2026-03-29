package com.github.zhuyiyi1990.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerIdemTest {

    public static void main(String[] args) {
        // 创建配置对象
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.58.100:9094");
        // 对生产的数据K, V进行序列化的操作
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.ACKS_CONFIG, "-1");
        configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configMap.put(ProducerConfig.RETRIES_CONFIG, 5);
        configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        configMap.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000);

        KafkaProducer<String, String> producer = new KafkaProducer<>(configMap);

        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "test", "key" + i, "value" + i
            );
            producer.send(record);
        }

        producer.close();
    }

}