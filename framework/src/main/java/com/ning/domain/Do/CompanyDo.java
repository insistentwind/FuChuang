package com.ning.domain.Do;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: qjn
 * @create: 2024/02/29 21:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDo {
    //登录账户
    private String username;
    //登录密码
    private String password;
    //账号所属的公司名
    private String companyName;
    //账号昵称
    private String nickName;
    //公司地址
    private String address;
    //公司介绍网址
    private String abbrHtml;
    //公司当前要招聘的职位网址
    private String abbrJob;
    //是否融资
    private String stage;
    //公司规模
    private String scale;
    //所属的行业
    private String industry;
    //职位招聘人
    private String hr;
    //公司类型
    private String type;
    //管理类型
    private String state;
    //启动资金
    private String fund;
}