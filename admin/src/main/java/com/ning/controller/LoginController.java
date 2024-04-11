package com.ning.controller;

import com.ning.domain.dto.UserDto;
import com.ning.domain.dto.UserLoginDto;
import com.ning.domain.entity.Menu;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.exception.BaseException;
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
@Api(tags = "登录接口")
@RequestMapping("system/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;


    // todo 修改账号密码
    /**
     * 用户后台登录相关接口
     * @return
     */
    @PostMapping("/user/login")
    @ApiOperation("登录接口")
    public Result<UserVo> login(@RequestBody UserLoginDto userLoginDto){
        if(userLoginDto.getUsername() == null || userLoginDto.getPassword() == null){
            throw new RuntimeException("用户名或密码不能为空");
        }
        return loginService.login(userLoginDto);
    }
    // 这个查询用户权限，只需要前端掉接口的时候回显进行路由就可以了，
    // 因为其他的情况是通过jwt解析出用户，在userDetail方法中每次登录就查询出这个用户所属角色的权限列表
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
        List<String> perms = menuService.selectPermsByUserId(userDto.getUser().getId());
//        List<String> perms =  menuService.selectPermsByUserId(userDto.getUser().getId());
        //根据用户id查询角色信息
        // 一个用户可能有多个角色
        List<String> roleKeyList = roleService.selectRoleKeyById(user.getId());
//        List<String> roleKeyList = null;

        //封装角色信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);

        return Result.success(adminUserInfoVo);
    }

    //用于当前用户所属角色的页面路由路径
    /**
     * 返回路由子目录的接口
     */
    @GetMapping("/getRouters")
    @ApiOperation("返回路由子目录的接口")
    public Result<RoutersVo> getRoutersInfo(){
        try {
            //查询当前角色的id
            Integer userId = SecurityUtils.getUserId();
            //查询menu结果是tree的形式，也就是子父菜单
            List<Menu> menuList = menuService.selectRouterMenuTreeByUserId(userId);
            //封装数据
            return Result.success(new RoutersVo(menuList));
        }
        catch (Exception e){
            throw new BaseException("用户未登录");
        }
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