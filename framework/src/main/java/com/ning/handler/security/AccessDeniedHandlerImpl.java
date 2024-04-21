package com.ning.handler.security;

import com.alibaba.fastjson.JSON;

import com.ning.domain.result.Result;
import com.ning.enums.AppHttpCodeEnum;
import com.ning.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.security.auth.message.AuthException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//认证失败的处理器
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        AuthException authException = new AuthException();
        //打印异常信息
        e.printStackTrace();
        System.out.println(httpServletRequest.getUserPrincipal());
        System.out.println(httpServletResponse);
//        authException.printStackTrace();
        //权限校验
        Result result = Result.success(AppHttpCodeEnum.NO_OPERATOR_AUTH);

        //响应
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
