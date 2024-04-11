package com.ning.domain.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/02/29 21:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CompanySignUpDo {

    //账户
    @ApiModelProperty(value = "用户名")
    private String username;
    //密码
    @ApiModelProperty(value = "密码")
    private String password;


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


    //Hr姓名
    @ApiModelProperty(value = "Hr姓名")
    private String name;
    //头像
    @ApiModelProperty(value = "头像")
    private String avatar;
    //性别（1男，0女）
    @ApiModelProperty(value = "性别（1男，0女）")
    private Integer sex;
    //出生日期
    @ApiModelProperty(value = "出生日期")
    private String birthday;
    //电话
    @ApiModelProperty(value = "电话")
    private String tele;
    //邮箱
    @ApiModelProperty(value = "邮箱")
    private String mail;
}