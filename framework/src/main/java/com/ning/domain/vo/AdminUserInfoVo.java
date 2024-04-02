package com.ning.domain.vo;

import io.swagger.annotations.ApiModelProperty;
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

//    private List<String> permissions;
    @ApiModelProperty(value = "权限")
    private MenuVo permissions;
    @ApiModelProperty(value = "角色")
    private List<String> roles;
    @ApiModelProperty(value = "用户信息")
    private UserInfoVo userInfoVo;
}
