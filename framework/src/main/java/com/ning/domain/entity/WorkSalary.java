package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (WorkSalary)表实体类
 *
 * @author makejava
 * @since 2024-03-28 17:04:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "薪资分类表",description = "")
@TableName("work_salary")
public class WorkSalary  {
    @TableId
    @ApiModelProperty(name = "1",notes = "")
    private Integer id;

    //薪资分类
    @ApiModelProperty(name = "薪资分类",notes = "")
    private String salary;



}

