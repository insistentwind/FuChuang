package com.ning.service;

import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserVo;

public interface LoginService {
    /**
     * 用户登录
     * @param user
     * @return
     */
    Result<UserVo> login(User user);
    /**
     * 用户注销接口
     * @return
     */
    Result<String> logout();
}
