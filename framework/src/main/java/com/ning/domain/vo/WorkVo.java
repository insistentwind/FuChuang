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

    private Integer id;

    private String title;

    private String salary;

    private String education;

    private String description;

    private String address;

    private String link;
}
