package com.ning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qjn
 * @Date: 2024/1/9 23:30
 */
@Data
public class WorkDto {

    private int pageNum;

    //每页显示记录数
    private int pageSize;

    private Integer id;

    private String company;

    private String title;

    private String salary;

    private String education;

    private String description;

    private String address;

    private String link;
}
