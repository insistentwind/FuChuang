package com.ning.domain.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: qjn
 * @Date: 2024/1/24 23:35
 */
@Data
@Accessors(chain = true)
public class CompanyVo {

    private Integer id;
    //加密id
    private String encryptBrandid;
    //行业
    private String brandIndustry;
    //公司名称
    private String brandName;
    //公司图标
    private String brandLogo;
    //公司规模
    private String brandScaleName;


}
