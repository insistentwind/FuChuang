package com.ning.domain.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * 职位画像(Drwa)表实体类
 *
 * @author makejava
 * @since 2024-04-09 18:23:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "职位画像",description = "")
@TableName("work_draw")
public class Draw {
    @TableId
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

