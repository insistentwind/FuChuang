package com.ning.service.impl;

import com.ning.service.PermissionService;
import com.ning.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/29 23:54
 **/
@Service("ps")
public class PermissionServiceImpl implements PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission){
        //判断当前用户是否具有permission

        //如果是超级管理员,直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则，获取当前登录用户所具有的权限列表，判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        //调用这个方法判断是否有这个权限
        return permissions.contains(permission);
    }
}