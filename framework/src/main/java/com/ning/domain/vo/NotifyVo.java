package com.ning.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: qjn
 * @create: 2024/03/05 20:42
 **/
@Data
public class NotifyVo {
    //消息内容
    private String content;
    //消息是否已读
    private Integer isRead;
    //时间
    private LocalDateTime time;
}