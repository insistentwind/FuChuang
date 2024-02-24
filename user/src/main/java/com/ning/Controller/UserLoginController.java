package com.ning.Controller;

import com.ning.domain.dto.ResumeVo;
import com.ning.domain.dto.UserLoginDto;
import com.ning.domain.dto.UserRegisterDto;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserVo;
import com.ning.enums.AppHttpCodeEnum;
import com.ning.exception.SystemException;
import com.ning.service.UserService;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: qjn
 * @Date: 2024/1/16 12:08
 */
@RestController
@Slf4j
@Api(tags = "登录相关接口")
@RequestMapping
public class UserLoginController {
    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * @param userLoginDto
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录接口")
    public Result<UserVo> login(@RequestBody UserLoginDto userLoginDto){
        log.info("用户登录:{}",userLoginDto);
        User user = BeanCopyUtils.copyBean(userLoginDto, User.class);
        if(!StringUtils.hasText(user.getUsername()) ||
                !StringUtils.hasText(user.getPassword())){
            //提示需要传输用户名或者密码
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME_OR_PASSWORD);
        }
        return userService.login(user);
    }

    /**
     * 用户注册
     * @param userRegisterDto
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<String> userRegister(@RequestBody UserRegisterDto userRegisterDto){
        log.info("用户注册的信息：{}",userRegisterDto);
        User user = BeanCopyUtils.copyBean(userRegisterDto, User.class);
        return userService.register(user);
    }

    /**
     * 获取当前用户的简历信息
     * @return
     */
    @ApiOperation("获取当前用户的简历信息")
    @GetMapping("/resume")
    public Result<ResumeVo> getUserResume(){
        log.info("获取当前用户的简历信息");
        return userService.getReusme();
    }

    /**
     * 修改当前用户的信息
     * @param user
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改当前用户的信息")
    public Result<String> update(@RequestBody User user){
        log.info("修改的用户信息是：{}",user);
        return userService.updateByUser(user);
    }
}
