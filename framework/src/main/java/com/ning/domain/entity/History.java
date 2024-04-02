package com.ning.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (History)表实体类
 *
 * @author makejava
 * @since 2024-03-01 15:45:53
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "用户查看职位历史",description = "")
@TableName("history")
public class History {
    /**  */
    @ApiModelProperty(name = "",notes = "")
    @TableId
    private Integer id ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Integer userId ;
    /** 职位id */
    @ApiModelProperty(name = "职位id",notes = "")
    private Integer workId ;
    /** 职位名称 */
    @ApiModelProperty(name = "职位名称",notes = "")
    private String title ;



}

