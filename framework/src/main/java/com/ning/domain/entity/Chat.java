package com.ning.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Chat)表实体类
 *
 * @author makejava
 * @since 2024-02-25 22:13:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat")
public class Chat  {
    @TableId
    private Integer id;

    //消息内容
    private String content;
    //发送者id
    private Integer senderId;
    //接受者id
    private Integer recvId;
    //发送者的信息
    private String selfInfo;
    //接受者的信息
    private String target;
    //消息发送时间
    private LocalDateTime timestamp;
    //0未读，1已读
    private Integer status;

    private Integer delFlag;

}

