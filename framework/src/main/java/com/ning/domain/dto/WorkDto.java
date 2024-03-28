package com.ning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * @Author: qjn
 * @Date: 2024/1/9 23:30
 */
@Data
public class WorkDto {
    //分页大小
    private int pageNum;
    //每页显示记录数
    private int pageSize;

    //id
    private Integer id;


    //分类id
    private String classifyId;

    //    //max分类
//    private String bigClassify;
//    //middle分类
//    private String midClassify;
//    //子分类
//    private String smallClassify;
    //薪资分类
    private Integer salary;

    //职位名称
    private String title;
    //学历分类
    private Integer education;
    //工作经验要求
    private Integer jobExperience;
    //工作地点id
    private Integer cityName;



}
