package com.github.zhuyiyi1990.controller;

import cn.hutool.json.JSONUtil;
import com.github.zhuyiyi1990.config.SpringBootKafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // {"data": {"name": "张三","age": 18}}
    @PostMapping(value = "/produce", produces = "application/json")
    @ResponseBody
    public String produce(@RequestBody Object obj) {
        try {
            String obj2String = JSONUtil.toJsonStr(obj);
            kafkaTemplate.send(SpringBootKafkaConfig.TOPIC_TEST, obj2String);
            return "success";
        } catch (Exception e) {
            e.getMessage();
        }
        return "success";
    }

}