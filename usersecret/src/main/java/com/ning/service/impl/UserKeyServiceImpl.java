package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.entity.UserKey;
import com.ning.mapper.UserKeyMapper;
import com.ning.service.UserKeyService;

import org.springframework.stereotype.Service;

/**
 * (UserKey)表服务实现类
 *
 * @author makejava
 * @since 2024-04-02 22:51:12
 */
@Service("userKeyService")
public class UserKeyServiceImpl extends ServiceImpl<UserKeyMapper, UserKey> implements UserKeyService {

}

