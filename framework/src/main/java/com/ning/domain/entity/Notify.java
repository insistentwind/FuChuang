package com.ning.domain.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@ApiModel(value = "消息表",description = "")
@TableName("notify")
public class Notify  {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 消息内容 */
    @ApiModelProperty(name = "消息内容",notes = "")
    private String content ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Integer userId ;
    /** 消息是否已读(0未读，1已读) */
    @ApiModelProperty(name = "消息是否已读(0未读，1已读)",notes = "")
    private Integer isRead ;
    /** 通知时间 */
    @ApiModelProperty(name = "通知时间",notes = "")
    private LocalDateTime time ;
    /** 删除标记 */
    @ApiModelProperty(name = "删除标记",notes = "")
    private Integer delFlag ;



}

