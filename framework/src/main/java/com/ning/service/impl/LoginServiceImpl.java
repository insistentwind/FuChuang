package com.ning.service.impl;

import com.ning.constants.SystemConstants;
import com.ning.domain.dto.UserDto;
import com.ning.domain.dto.UserLoginDto;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserVo;
import com.ning.service.LoginService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.JwtUtil;
import com.ning.utils.RedisCache;
import com.ning.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: qjn
 * @create: 2024/03/06 22:59
 **/
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public Result<UserVo> login(UserLoginDto userLoginDto) {
        //这个适用于封装对应的信息参数
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword());
        //调用这个方法开始认证
        Authentication authenticate = authenticationManager.authenticate(token);
//        System.out.println(authenticate);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户认证失败,用户名或密码错误");
        }
        //下面就认证成功，把用户信息放到token中
        UserDto userDto = (UserDto) authenticate.getPrincipal();
        if(Objects.equals(userDto.getUser().getIsCompany(), SystemConstants.IS_NOT_COMPANY)){
            throw new RuntimeException("认证失败，非管理员用户");
        }
        Integer userId = userDto.getUser().getId();
        String jwt = JwtUtil.createJWTBackground(userId.toString());

        redisCache.setCacheObject(SystemConstants.ADMIN_LOGIN + userId,userDto);
//        UserInfoVo userInfoVo = new UserInfoVo();
//        BeanUtils.copyProperties(loginUser.getUser(), userInfoVo);
//        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt,userInfoVo);
        User user = userDto.getUser();
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        userVo.setJwt(jwt);
        return Result.success(userVo);
    }
    /**
     * 用户注销接口
     * @return
     */
    @Override
    public Result<String> logout() {
        UserDto loginUser = SecurityUtils.getLoginUser();
        redisCache.deleteObject(SystemConstants.ADMIN_LOGIN + loginUser.getUser().getId());
        return Result.success("注销成功");
    }
}