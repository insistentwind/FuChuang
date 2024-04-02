package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "1")
    private Integer id;
    //行业
    @ApiModelProperty(value = "行业")
    private String brandIndustry;
    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String brandName;
    //公司图标
    @ApiModelProperty(value = "公司图标")
    private String brandLogo;
    //公司规模
    @ApiModelProperty(value = "公司规模")
    private String brandScaleName;


}
