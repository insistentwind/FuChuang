package com.ning.domain.vo;

import com.ning.domain.entity.Role;
import com.ning.domain.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/30 22:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRoleInfoVo {
    @ApiModelProperty(value = "角色")
    private List<Role> roles;
    @ApiModelProperty(value = "角色id")
    private List<String> roleIds;
    @ApiModelProperty(value = "用户")
    private User user;
}