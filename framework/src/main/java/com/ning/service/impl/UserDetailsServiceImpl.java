package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.constants.MessageConstant;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.User;
import com.ning.exception.BaseException;
import com.ning.mapper.UserMapper;
import com.ning.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: qjn
 * @Date: 2024/1/16 13:02
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            log.info("用户名是:{}",username);
            wrapper.eq(User::getUsername,username);
            User user = userService.getOne(wrapper);

            if(Objects.isNull(user)){
                throw new BaseException(MessageConstant.ACCOUNT_NOT_FOUND);
            }
            log.info("账户存在");

            return new UserDto(user);
    }
}
