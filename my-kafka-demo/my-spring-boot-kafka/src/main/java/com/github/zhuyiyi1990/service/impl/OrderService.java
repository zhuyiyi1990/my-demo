package com.github.zhuyiyi1990.service.impl;

import com.github.zhuyiyi1990.service.IOrderService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public void saveOrder(String id, Object message) {
        kafkaTemplate.send(new ProducerRecord("topic04", id, message));
    }

}