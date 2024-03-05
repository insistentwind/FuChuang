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

    private Integer id;
    //职位名
    private String title;
    //学历要求
    private String education;
    //工作地点
    private String address;
    //最大薪资
    private String maxSa;
    //最低薪资
    private String minSa;
}
