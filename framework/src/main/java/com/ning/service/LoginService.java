package com.ning.service;

import com.ning.domain.dto.UserLoginDto;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserVo;

public interface LoginService {
    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    Result<UserVo> login(UserLoginDto userLoginDto);
    /**
     * 用户注销接口
     * @return
     */
    Result<String> logout();
}
