package com.ning.domain.message.impl.request;// SendResponse.java

import com.ning.domain.message.Message;
import lombok.Data;

@Data
public class SendToUserRequest implements Message {

    public static final String TYPE = "SEND_TO_USER_REQUEST";

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;
    
    // ... 省略 set/get 方法
     
}