package com.baizhi.tests;

import com.baizhi.KafkaSpringBootApplication;
import com.baizhi.service.IOrderService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest(classes = {KafkaSpringBootApplication.class})
public class KafkaTempolateTests {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private IOrderService orderService;

    @Test
    public void testOrderService() {
        orderService.saveOrder("001", "baizhi edu ");
    }

    @Test
    public void testKafkaTemplate() {
        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
            @Override
            public Object doInOperations(KafkaOperations kafkaOperations) {
                return kafkaOperations.send(new ProducerRecord("topic01", "002", "this is a demo"));
            }
        });
    }

}