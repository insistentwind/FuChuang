package com.ning.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.domain.entity.UserKey;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KeyHttpUtils {
    /**
     * post请求
     * @param url
     * @param requestBody
     * @return
     */
    public static UserKey sendPostRequest(String url, String requestBody) {
        try {
            // 创建 HttpClient 实例
            HttpClient client = HttpClient.newHttpClient();
            
            // 创建 HTTP 请求，设置请求体内容
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url)) // 目标接口地址
                    .header("Content-Type", "application/json") // 设置请求头，指定请求体类型为 JSON
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // 设置请求体内容
                    .build();
            
            // 发送 HTTP POST 请求并获取响应
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String content = response.body();

            // 使用 JSON 序列化/反序列化库将响应体字符串转换为对象
            ObjectMapper objectMapper = new ObjectMapper();
            UserKey myEntity = objectMapper.readValue(content, UserKey.class);
            // 返回响应内容
            return myEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get请求
     * 拿到用户id对应的密钥
     * @param url
     * @return
     */
    public static UserKey sendGetRequest(String url,Integer userId) {
        try {
            // 创建 HttpClient 实例
            HttpClient client = HttpClient.newHttpClient();

            // 创建 HTTP 请求
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url+ "/" + userId.toString()) ) // 目标接口地址
                    .build();

            // 发送 HTTP GET 请求并获取响应
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String content = response.body();
            if (content == null || content.isEmpty()) {
                // 如果响应为空，则返回 null 或者抛出异常，视需求而定
                return null; // 或者抛出异常，比如 throw new RuntimeException("Empty response");
            }
            // 使用 JSON 序列化/反序列化库将响应体字符串转换为对象
            ObjectMapper objectMapper = new ObjectMapper();
            UserKey myEntity = objectMapper.readValue(content, UserKey.class);

            // 返回响应内容
            return myEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // 定义请求体内容
        String requestBody = "{\"key1\": \"value1\", \"key2\": \"value2\"}";
        
        // 调用发送 POST 请求的方法
//        String response = KeyHttpUtils.sendPostRequest("https://api.example.com/data", UserKey.class);
        
//        // 打印响应内容
//        if (response != null) {
//            System.out.println("Response body: " + response);
//        } else {
//            System.out.println("Failed to send POST request.");
//        }
    }
}