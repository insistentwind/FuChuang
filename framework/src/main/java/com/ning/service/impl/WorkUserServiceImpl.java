package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.WorkUserMapper;
import com.ning.domain.entity.WorkUser;
import org.springframework.stereotype.Service;
import com.ning.service.WorkUserService;

/**
 * (WorkUser)表服务实现类
 *
 * @author makejava
 * @since 2024-03-19 22:40:05
 */
@Service("workUserService")
public class WorkUserServiceImpl extends ServiceImpl<WorkUserMapper, WorkUser> implements WorkUserService {

}

