package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
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
@Accessors(chain = true)
@ApiModel(value = "websocket聊天",description = "")
@TableName("chat")
public class Chat implements Serializable,Cloneable{
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 消息内容 */
    @ApiModelProperty(name = "消息内容",notes = "")
    private String content ;
    /** 发送者id */
    @ApiModelProperty(name = "发送者id",notes = "")
    private Integer senderId ;
    /** 接受者id */
    @ApiModelProperty(name = "接受者id",notes = "")
    private Integer recvId ;
    /** 发送者的信息 */
    @ApiModelProperty(name = "发送者的信息",notes = "")
    private String selfInfo ;
    /** 接受者的信息 */
    @ApiModelProperty(name = "接受者的信息",notes = "")
    private String target ;
    /** 消息发送时间 */
    @ApiModelProperty(name = "消息发送时间",notes = "")
    private LocalDateTime timestamp ;
    /** 0未读，1已读 */
    @ApiModelProperty(name = "0未读，1已读",notes = "")
    private Integer status ;
    /**  */
    @ApiModelProperty(name = "",notes = "")
    private Integer delFlag ;

}

