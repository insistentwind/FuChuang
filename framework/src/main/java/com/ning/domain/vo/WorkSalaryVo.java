package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/30 19:14
 **/
@Data
@Accessors(chain = true)
public class WorkSalaryVo {
    private Integer id;

    //薪资分类
    @ApiModelProperty(value = "薪资分类")
    private String salary;


}