package com.github.zhuyiyi1990;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyElasticSearchDemoApplicationTests {

    @Test
    void contextLoads() throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.58.100", 9200, "http"))
        );

        // 关闭ES客户端
        esClient.close();
    }

}