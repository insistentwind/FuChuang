package com.ning.utils;

import com.ning.domain.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author qjn
 */
public class SecurityUtils
{

    /**
     * 获取用户
     **/
    public static UserDto getLoginUser()
    {
        return (UserDto) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Integer id = getLoginUser().getUser().getId();
        return id != null && id.equals(1);
    }

    public static Integer getUserId() {
        return getLoginUser().getUser().getId();
    }
}