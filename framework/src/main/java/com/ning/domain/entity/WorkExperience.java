package com.ning.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * (WorkExperience)表实体类
 *
 * @author makejava
 * @since 2024-03-28 17:04:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("work_experience")
public class WorkExperience  {
    @TableId
    private Integer id;

    //工作经验
    private String jobExperience;



}

