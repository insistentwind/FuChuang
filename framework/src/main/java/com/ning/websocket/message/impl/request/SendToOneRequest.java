package com.ning.domain.message.impl.request;// SendToOneRequest.java

import com.ning.domain.message.Message;
import lombok.Data;

@Data
public class SendToOneRequest implements Message {

    public static final String TYPE = "SEND_TO_ONE_REQUEST";

    /**
     * 发送给的用户
     */
    private String toUser;
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