package com.ning.handler.security;

import com.alibaba.fastjson.JSON;
import com.ning.domain.result.Result;
import com.ning.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//认证失败的处理器
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //打印异常信息
        e.printStackTrace();
        Result result = null;
        if(e instanceof BadCredentialsException){
            //用户名密码错误
            result = Result.error(e.getMessage());
        }
        else if(e instanceof InsufficientAuthenticationException)
        {
            //需要登录进行操作
            result = Result.error("需要登录");
        }
        else {
            result = Result.error("认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
