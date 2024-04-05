package com.ning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.UserKey;
import com.qiniu.util.Json;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author: qjn
 * @create: 2024/04/05 19:28
 **/
@SpringBootTest
public class requestTest {



    @Test
    public void request() throws IOException, InterruptedException {

        String url = SystemConstants.PYTHON_URL;

        Integer workId = 1;

        System.out.println(url + " " + workId);
        // 创建 HttpClient 实例
        HttpClient client = HttpClient.newHttpClient();

        // 创建 HTTP 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + workId.toString()) ) // 目标接口地址
                .build();

        // 发送 HTTP GET 请求并获取响应
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        // 创建一个ObjectMapper对象，用于将JSON字符串解析为JSON对象
        ObjectMapper objectMapper = new ObjectMapper();
        // 将HTTP响应内容解析为JSON对象
        JsonNode jsonNode = objectMapper.readTree(content);
        String code = jsonNode.get("code").asText();
        System.out.println("code: " + code);

//        // 使用 JSON 序列化/反序列化库将响应体字符串转换为对象
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserKey myEntity = objectMapper.readValue(content, UserKey.class);
    }

}