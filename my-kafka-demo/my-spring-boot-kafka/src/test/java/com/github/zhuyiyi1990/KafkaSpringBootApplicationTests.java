package com.github.zhuyiyi1990;

import com.github.zhuyiyi1990.service.IOrderService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest(classes = {KafkaSpringBootApplication.class})
class KafkaSpringBootApplicationTests {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private IOrderService orderService;

    @Test
    void testOrderService() {
        orderService.saveOrder("001", "order-001");
    }

    @Test
    void testKafkaTemplate() {
        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
            @Override
            public Object doInOperations(KafkaOperations kafkaOperations) {
                return kafkaOperations.send(new ProducerRecord("topic01", "002", "this is a demo"));
            }
        });
    }

}