package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/04/09 18:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class DrawVo {

    private Integer id;

    //职位id
    @ApiModelProperty(value = "职位id")
    private Integer workId;
    //技能列表
    @ApiModelProperty(value = "技能列表")
    private String skills;
    //基本信息
    @ApiModelProperty(value = "基本信息")
    private String basicTags;
    //教育经历
    @ApiModelProperty(value = "教育经历")
    private String educationTags;
}