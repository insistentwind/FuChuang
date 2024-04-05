package com.ning.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 职位表;
 *
 * @author : qjn
 * @date : 2024-4-5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "职位表", description = "")
@TableName("work_log")
public class WorkLog implements Serializable {

    private static final long serialVersionUID = 1L; // 设置序列化ID为1L

    /**
     *
     */
    @ApiModelProperty(value = "", notes = "")
    @TableId
    private Integer id;

    @ApiModelProperty(value = "职位id")
    private Integer workId;
    @ApiModelProperty(value = "操作标识位(0是删除，1是插入，2是更新操作)", notes = "")
    private Integer tagFlag;

}