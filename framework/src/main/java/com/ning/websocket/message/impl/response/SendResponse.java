package com.ning.domain.message.impl.response;// SendResponse.java

import com.ning.domain.message.Message;
import lombok.Data;

@Data

public class SendResponse implements Message {

    public static final String TYPE = "SEND_RESPONSE";

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;
    
    // ... 省略 set/get 方法
    
}