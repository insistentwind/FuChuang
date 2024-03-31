package com.ning.domain.vo;

import com.ning.domain.entity.Role;
import com.ning.domain.entity.User;
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

    private List<Role> roles;

    private List<String> roleIds;

    private User user;
}