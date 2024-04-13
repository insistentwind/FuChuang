package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/3 13:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleVo {
    @ApiModelProperty(value = "1")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String username;
    //昵称
    @ApiModelProperty(value = "昵称")
    private String name;
    //密码
    @ApiModelProperty(value = "密码")
    private String password;
    //用户性别（0男，1女，2未知）
    @ApiModelProperty(value = "用户性别（0男，1女，2未知）")
    private String sex;
    //账号状态（0正常 1停用）
    @ApiModelProperty(value = "账号状态（0正常 1停用）")
    private String states;
    //邮箱
    @ApiModelProperty(value = "邮箱")
    private String mail;
    //手机号
    @ApiModelProperty(value = "手机号")
    private String tele;

    @ApiModelProperty(value = "角色id")
    private List<Long> roleIds;

}
