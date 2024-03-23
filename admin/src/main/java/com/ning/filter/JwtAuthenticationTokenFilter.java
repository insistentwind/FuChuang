package com.ning.filter;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.util.StringUtils;
import com.ning.constants.MessageConstant;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.UserDto;
import com.ning.domain.result.Result;
import com.ning.utils.JwtUtil;
import com.ning.utils.RedisCache;
import com.ning.utils.WebUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader(SystemConstants.TOKEN);

        if(StringUtils.isNullOrEmpty(token)){
            //说明该接口不需要登录，直接放行
            //放行了后并不是说明可以直接登录，没有设置页面路径匿名访问权限的话，会被拦截报异常
            filterChain.doFilter(request,response);
//            log.info("该接口不需要登录,token为空，此处直接放行");
            return;
        }
        //解析获取userId
        Claims claims = null;

        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时 或者 token非法

            //需要重新登陆，响应给前端
            log.info("token超时 或者 token非法，需要重新登陆");
            Result<String> result = Result.error(MessageConstant.USER_NOT_LOGIN);

            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        String userId = claims.getSubject();
        //从redis中获取用户信息
        UserDto userDto = redisCache.getCacheObject(SystemConstants.USER_LOGIN + userId);

        //如果获取不到redis中的值
        if(Objects.isNull(userDto)){
            //说明登录过期
            log.info("登录过期");
            Result<String> result = Result.error(MessageConstant.USER_NOT_LOGIN);

            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        if (userDto.getUser().getIsCompany() != 2){
            log.info("没有权限");

            Result<String> result = Result.error(MessageConstant.HAS_NO_PERMS);
            WebUtils.renderString(response,JSON.toJSONString(result));
            return;
        }

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("校验成功，放行");
        //放行
        filterChain.doFilter(request,response);
    }
}
