package com.ning.domain.message.impl.response;

import com.ning.domain.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/03/09 21:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse implements Message {
    public static final String TYPE = "AUTH_RESPONSE";

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;
}