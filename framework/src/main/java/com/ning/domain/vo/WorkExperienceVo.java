package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/28 18:32
 **/
@Data
@Accessors(chain = true)
public class WorkExperienceVo {
    @ApiModelProperty(value = "1")
    private Integer id;

    //工作经验
    @ApiModelProperty(value = "工作经验")
    private String jobExperience;
}