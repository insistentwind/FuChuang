package com.ning.websocket.message.impl.request;

import com.ning.websocket.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/03/09 21:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest implements Message {

    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证 Token
     */
    private String accessToken;

    public AuthRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
    // ... 省略 set/get 方法

}