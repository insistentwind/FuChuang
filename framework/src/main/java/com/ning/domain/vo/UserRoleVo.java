package com.ning.domain.vo;

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
    private Integer id;

    private String username;
    //昵称
    private String name;
    //密码
    private String password;
    //用户性别（0男，1女，2未知）
    private String sex;
    //账号状态（0正常 1停用）
    private String states;
    //邮箱
    private String mail;
    //手机号
    private String tele;

    private List<Integer> roleIds;

}
