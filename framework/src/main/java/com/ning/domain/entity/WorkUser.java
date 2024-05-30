package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (WorkUser)表实体类
 *
 * @author makejava
 * @since 2024-03-19 22:40:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "职位-用户简历投递表",description = "")
@TableName("work_user")
@Builder
public class WorkUser  {
    /**  */
    @ApiModelProperty(name = "1",notes = "")
    @TableId
    private Integer id ;
    /** 职位id */
    @ApiModelProperty(name = "职位id",notes = "")
    private Integer workId ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Integer userId ;
    /** 用户简历id */
    @ApiModelProperty(name = "用户简历id",notes = "")
    private Integer resumeId ;
    @ApiModelProperty(name = "是否已读(0否1是)",notes = "")
    private Integer isRead;

    @ApiModelProperty(value = "简历投递时间")
    private LocalDateTime createTime;

}

