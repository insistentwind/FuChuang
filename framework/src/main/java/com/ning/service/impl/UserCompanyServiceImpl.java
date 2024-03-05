package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.UserCompany;
import com.ning.mapper.UserCompanyMapper;
import com.ning.service.UserCompanyService;
import org.springframework.stereotype.Service;

/**
 * (UserCompany)表服务实现类
 *
 * @author makejava
 * @since 2024-03-05 21:25:14
 */
@Service("userCompanyService")
public class UserCompanyServiceImpl extends ServiceImpl<UserCompanyMapper, UserCompany> implements UserCompanyService {

}

