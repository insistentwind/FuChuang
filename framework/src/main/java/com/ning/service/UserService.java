package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserVo;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-01-16 12:09:23
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录接口
     * @param user
     * @return
     */
    Result<UserVo> login(User user);
    /**
     * 用户注册
     * @param user
     * @return
     */
    Result<String> register(User user);
}

