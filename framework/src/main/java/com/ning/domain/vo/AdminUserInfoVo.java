package com.ning.domain.vo;

import com.ning.domain.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/11/29 19:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//开启链式调用
@Accessors(chain = true)
@Builder
public class AdminUserInfoVo {

    private List<String> permissions;

    private List<String> roles;

    private UserInfoVo user;
}
