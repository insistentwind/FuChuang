package com.ning.controller;

import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.Menu;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.service.LoginService;
import com.ning.service.MenuService;
import com.ning.service.RoleService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/06 22:57
 **/
@RestController
@Slf4j
@Api(tags = "后台登录相关接口")
@RequestMapping("system/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    /**
     * 用户后台登录相关接口
     * @return
     */
    @PostMapping("/user/login")
    @ApiOperation("用户后台登录相关接口")
    public Result<UserVo> login(@RequestBody User user){
        log.info("后台用户登录:{}",user);
        if(user.getUsername() == null || user.getPassword() == null){
            throw new RuntimeException("用户名或密码不能为空");
        }
        return loginService.login(user);
    }

    /**
     * 查询当前登录的用户的信息，权限等
     * @return
     */
    @GetMapping("/getInfo")
    @ApiOperation("查询当前登录的用户的信息，权限等")
    public Result<AdminUserInfoVo> getInfo(){
        log.info("查询当前登录的用户的信息，权限等");
        //获取当前登录的用户
        UserDto userDto = SecurityUtils.getLoginUser();
        User user = userDto.getUser();
        //根据用户id查询权限信息
        MenuVo perms = menuService.selectPermsByUserId(userDto.getUser().getId());
//        List<String> perms =  menuService.selectPermsByUserId(userDto.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyById(user.getId());
//        List<String> roleKeyList = null;

        //封装角色信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return Result.success(adminUserInfoVo);
    }


    /**
     * 返回路由子目录的接口
     */
    @GetMapping("/getRouters")
    @ApiOperation("返回路由子目录的接口")
    public Result<RoutersVo> getRoutersInfo(){
        //查询当前角色的id
        Integer userId = SecurityUtils.getUserId();
        //查询menu结果是tree的形式，也就是子父菜单
        List<Menu> menuList = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据
        return Result.success(new RoutersVo(menuList));
//        return ResponseResult.okResult(menuList);
    }

    /**
     * 用户注销接口
     * @return
     */
    @ApiOperation("用户注销接口")
    @GetMapping ("/user/logout")
    public Result<String> logOut(){
        return loginService.logout();
    }
}