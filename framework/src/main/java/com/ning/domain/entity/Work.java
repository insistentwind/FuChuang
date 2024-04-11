package com.ning.domain.entity;


import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Work)表实体类
 *
 * @author makejava
 * @since 2024-02-01 16:24:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("work")
@Accessors(chain = true)
public class Work {

    @TableField(exist = false)
    private Integer companyId;

    @TableField(exist = false)
    private String companyName;
    @TableField(exist = false)
    private String workId;


    /**
     *
     */
    @ApiModelProperty(name = "", notes = "")
    @TableId
    private Integer id;
    /**
     * 分类id
     */
    @ApiModelProperty(name = "分类id", notes = "")
    private Integer classifyId;
    /**
     * 职位介绍
     */
    @ApiModelProperty(name = "职位介绍", notes = "")
    private String description;
    /**
     *
     */
    @ApiModelProperty(name = "", notes = "")
    private String title;
    /**
     * 学历要求
     */
    @ApiModelProperty(name = "学历要求", notes = "")
    private Integer education;
    /**
     * 工作经验要求
     */
    @ApiModelProperty(name = "工作经验要求", notes = "")
    private Integer jobExperience;
    /**
     * 薪资分类
     */
    @ApiModelProperty(name = "薪资分类", notes = "")
    private Integer salary;
    /**
     * 薪资水平
     */
    @ApiModelProperty(name = "薪资水平", notes = "")
    private String salaryDesc;
    /**
     * 技术要求
     */
    @ApiModelProperty(name = "技术要求", notes = "")
    private String skills;
    /**
     * 福利列表
     */
    @ApiModelProperty(name = "福利列表", notes = "")
    private String welfareList;
    /**
     * 工作地点id
     */
    @ApiModelProperty(name = "工作地点id", notes = "")
    private Integer cityName;
    /**
     * 工作地区
     */
    @ApiModelProperty(name = "工作地区", notes = "")
    private String areaDistrict;
    /**
     * 工作地点
     */
    @ApiModelProperty(name = "工作地点", notes = "")
    private String businessDistrict;
    /**
     * 链接
     */
    @ApiModelProperty(name = "链接", notes = "")
    private String href;
    /**
     * hr姓名
     */
    @ApiModelProperty(name = "hr姓名", notes = "")
    private String bossName;
    /**
     * hr职位
     */
    @ApiModelProperty(name = "hr职位", notes = "")
    private String bossTitle;
    /**
     * 创建人
     */
    @ApiModelProperty(name = "创建人", notes = "")
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "创建时间", notes = "")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(name = "更新人", notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(name = "更新时间", notes = "")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 浏览量
     */
    @ApiModelProperty(name = "浏览量", notes = "")
    private Long viewCount;
    /**
     * 删除标志位
     */
    @ApiModelProperty(name = "删除标志位", notes = "")
    private Integer delFlag;


    public Work(Integer id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }


    /**
     * job职位表更改
     * 原数据字段  --> 现数据字段
     * title -> description
     * job_name -> title
     * job_degree -> education
     */

}

