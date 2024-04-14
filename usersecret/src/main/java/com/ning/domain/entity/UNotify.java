package com.ning.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * 消息表(UNotify)表实体类
 *
 * @author makejava
 * @since 2024-04-14 13:02:49
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("notify")
public class UNotify {
    @TableId
    private Integer id;

    //消息内容
    private String content;
    //用户id
    private Integer userId;
    //消息是否已读(0未读，1已读)
    private Integer isRead;
    //通知时间
    private LocalDateTime time;
    //删除标记
    private Integer delFlag;



}

