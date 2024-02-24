package com.ning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: qjn
 * @Date: 2024/2/6 15:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    //用户名
    private String username;
    //密码
    private String password;
    //邮箱
    private String mail;
}
