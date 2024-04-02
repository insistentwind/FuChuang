package com.ning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {

    //用户id
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    //关注的公司的id
    @ApiModelProperty(value = "关注的公司的id")
    private Integer companyId;
}
