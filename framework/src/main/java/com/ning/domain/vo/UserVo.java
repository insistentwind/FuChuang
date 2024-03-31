package com.ning.domain.vo;

import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: qjn
 * @Date: 2024/1/16 12:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class UserVo {
    private Integer id;

    private String jwt;

    //姓名
    private String name;
    //密码
    private String password;
    // 修改时用（旧密码）
    private String oldPassword;
    //头像
    private String avatar;
    //求职状态（是否立刻到岗，1是，0否）
    private Integer status;
    //性别（1男，0女）
    private Integer sex;
    //身份（学生，社会人士）
    private String idCard;
    //出生日期
    private String birthday;
    //电话
    private String tele;
    //邮箱
    private String mail;
}
