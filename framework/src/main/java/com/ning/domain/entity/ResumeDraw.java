package com.ning.domain.entity;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
/**
 * 简历画像(ResumeDraw)表实体类
 *
 * @author makejava
 * @since 2024-04-11 17:09:40
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "简历画像实体")
@TableName("resume_draw")
public class ResumeDraw  {
    @TableId
    private Integer id;

    //对应的简历id
    @ApiModelProperty(value = "对应的简历id")
    private Integer resumeId;
    //大分类
    @ApiModelProperty(value = "大分类")
    private String bigClassify;
    //画像亮点
    @ApiModelProperty(value = "画像亮点")
    private String highLight;
    //错误
    @ApiModelProperty(value = "错误")
    private String isError;
    //技能
    @ApiModelProperty(value = "技能")
    private String skills;
    //小分类
    @ApiModelProperty(value = "小分类")
    private String smallClassify;



}

