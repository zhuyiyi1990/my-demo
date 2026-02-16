package com.github.zhuyiyi1990;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class MyElasticSearchDemoApplicationTests {

    private RestHighLevelClient esClient;

    @BeforeEach
    void connect() {
        // 1. 创建凭证提供者并设置用户名和密码
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY, // 该凭证适用于任何主机和端口
                new UsernamePasswordCredentials("elastic", "password") // 替换为你的实际用户名和密码
        );

        // 2. 构建HTTP客户端配置，将凭证提供者设置进去
        RestClientBuilder builder = RestClient.builder(
                        new HttpHost("192.168.58.100", 9200, "http")) // 根据你的ES协议调整：http或https
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        // 将凭证提供者设置给HttpClient
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        // 创建ES客户端
        esClient = new RestHighLevelClient(builder);
    }

    @AfterEach
    void disconnect() throws Exception {
        // 关闭ES客户端
        if (Objects.nonNull(esClient)) {
            esClient.close();
        }
    }

    @Test
    void contextLoads() {
    }

    // 创建索引
    @Test
    void testCreateIndex() throws Exception {
        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse createIndexResponse = esClient.indices().create(request, RequestOptions.DEFAULT);

        // 响应状态
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println("索引操作 ：" + acknowledged);
    }

}