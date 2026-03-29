package com.github.zhuyiyi1990.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class KafkaProducerCallbackTest {

    public static void main(String[] args) throws Exception {
        // 配置属性集合
        Map<String, Object> configMap = new HashMap<>();
        // 配置属性：Kafka服务器集群地址
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.58.100:9094");
        // 配置属性：Kafka生产的数据为KV对，所以在生产数据进行传输前需要分别对K,V进行对应的序列化操作
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 创建Kafka生产者对象，建立Kafka连接
        // 构造对象时，需要传递配置参数
        KafkaProducer<String, String> producer = new KafkaProducer<>(configMap);
        for (int i = 0; i < 10; i++) {
            // 准备数据,定义泛型
            // 构造对象时需要传递 【Topic主题名称】，【Key】，【Value】三个参数
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "test", "key" + i, "value" + i
            );
            // 生产（发送）数据
            Future<RecordMetadata> result = producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println("发送成功：" + metadata);
                }
            });
            /*// 可将异步发送变成同步发送，只有kafka成功接收一条数据后再发下一条数据
            result.get();*/
        }
        // 关闭生产者连接
        producer.close();
    }

}