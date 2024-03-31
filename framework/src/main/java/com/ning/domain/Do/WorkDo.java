package com.ning.domain.Do;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/03/29 23:45
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDo {

    private Integer id;
    //分类id
    private Integer classifyId;
    //职位介绍
    private String description;
    //职位名称
    private String title;
    //学历要求
    private Integer education;
    //工作经验要求
    private Integer jobExperience;
    //薪资分类
    private Integer salary;
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

}