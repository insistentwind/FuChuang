package com.ning.domain.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2024-01-16 14:27:09
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@Accessors(chain = true)
public class User  {
    @TableId
    private Integer id;

    //姓名
    private String name;
    //用户名
    private String username;
    //密码
    private String password;
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
    //感兴趣的职位ids
    private String interests;
    //简历投递历史记录
    private String uploadCollect;
    //简历查看历史记录
    private String seeCollect;
    //简历id
    private Integer resumeId;
    //注册时间
    private LocalDateTime createTime;
    //是否为公司hr(0否，1是)
    private Integer isCompany;


}

