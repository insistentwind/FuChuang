package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/19 22:14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserCompanyVo {

    /**
     * 职位id
     */
    private Integer workId;
    //公司名
    private String company;
    //职位名
    private String title;
    //薪资
    private String salary;
    //学历要求
    private String education;
    //浏览量
    private Long viewCount;
}