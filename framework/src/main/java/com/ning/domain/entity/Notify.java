package com.ning.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Notify)表实体类
 *
 * @author makejava
 * @since 2024-02-24 22:21:31
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("notify")
public class Notify  {
    @TableId
    private Integer id;

    //消息内容
    private String content;
    //用户名
    private Integer userId;
    //消息是否已读(0未读，1已读)
    private Integer isRead;
    //通知时间
    private LocalDateTime time;

    private Integer delFlag;



}

