package com.ning.domain.Do;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/03/29 23:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDo {



    private Integer id;

    //行业
    private String brandIndustry;
    //公司名称
    private String brandName;
    //公司图标
    private String brandLogo;
    //公司规模
    private String brandScaleName;

}