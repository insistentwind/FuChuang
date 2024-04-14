package com.ning.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Notify)表实体类
 *
 * @author makejava
 * @since 2024-02-24 19:34:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDto {

    Integer id;
    //消息内容
    @ApiModelProperty(value = "消息内容")
    private String content;
    //用户id
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    //消息是否已读
    @ApiModelProperty(value = "消息是否已读")
    private Integer isRead;

}
