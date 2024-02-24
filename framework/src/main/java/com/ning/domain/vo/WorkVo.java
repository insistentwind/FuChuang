package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qjn
 * @Date: 2024/1/15 17:17
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkVo {

    //公司名
    private String company;
    //职位名
    private String title;
    //薪资
    private String salary;
    //学历要求
    private String education;
    //职位描述
    private String description;
    //工作地点
    private String address;
    //链接
    private String link;
    //浏览量
    private Long viewCount;
}
