package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.UserPermitcompanyMapper;
import com.ning.domain.entity.UserPermitcompany;
import com.ning.service.UserPermitcompanyService;
import org.springframework.stereotype.Service;

/**
 * (UserPermitcompany)表服务实现类
 *
 * @author makejava
 * @since 2024-04-03 21:53:51
 */
@Service("userPermitcompanyService")
public class UserPermitcompanyServiceImpl extends ServiceImpl<UserPermitcompanyMapper, UserPermitcompany> implements UserPermitcompanyService {

}

