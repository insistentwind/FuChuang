package com.ning.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/04/10 21:53
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "公司发布删除职位消息",description = "")
public class PositionMessage {

    @ApiModelProperty(value = "公司名")
    private String companyName;
    @ApiModelProperty(value = "信息")
    private String message;
}