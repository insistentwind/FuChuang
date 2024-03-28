package com.ning.domain.entity;


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
@TableName("work_salary")
public class WorkSalary  {
    @TableId
    private Integer id;

    //薪资分类
    private String salary;



}

