package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.UserResumeMapper;
import com.ning.domain.entity.UserResume;
import org.springframework.stereotype.Service;
import com.ning.service.UserResumeService;

/**
 * (UserResume)表服务实现类
 *
 * @author makejava
 * @since 2024-03-14 21:38:21
 */
@Service("userResumeService")
public class UserResumeServiceImpl extends ServiceImpl<UserResumeMapper, UserResume> implements UserResumeService {

}

