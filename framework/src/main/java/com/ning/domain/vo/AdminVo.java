package com.ning.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: qjn
 * @create: 2024/03/12 19:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminVo {

    //姓名
    private String username;
    //头像
    private String avatar;
    //性别（1男，0女）
    private Integer sex;
    //电话
    private String tele;
}