package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: qjn
 * @Date: 2023/12/3 13:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageVo {
    //用户名
    private String username;

    private String status;

    //手机号
    private String tele;

    private Integer pageNum;

    private Integer pageSize;
}
