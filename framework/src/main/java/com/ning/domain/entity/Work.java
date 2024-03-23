package com.ning.domain.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Work  {

    @TableId
    private Integer id;

    @TableField(exist = false)
    private Integer companyId;

    @TableField(exist = false)
    private String companyName;

    //分类id
    private Integer classifyId;
    //职位介绍
    private String description;
    //职位名称
    private String title;
    //学历要求
    private String education;
    //工作经验要求
    private String jobExperience;
    //薪资水平
    private String salaryDesc;
    //技术要求
    private String skills;
    //福利列表
    private String welfareList;
    //工作地点id
    private Integer cityName;
    //工作地区
    private String areaDistrict;
    //工作地点
    private String businessDistrict;
    //链接
    private String href;
    //hr姓名
    private String bossName;
    //hr职位
    private String bossTitle;
    //唯一id
    private String encryptBrandid;
    //浏览量
    private Long viewCount;

    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //    //更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
    //    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //删除标志位
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

