package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.UserCompany;
import com.ning.mapper.UserCompanyMapper;
import com.ning.service.UserCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (UserCompany)表服务实现类
 *
 * @author makejava
 * @since 2024-03-05 21:25:14
 */
@Service("userCompanyService")
public class UserCompanyServiceImpl extends ServiceImpl<UserCompanyMapper, UserCompany> implements UserCompanyService {
    @Autowired
    private UserCompanyMapper userCompanyMapper;
    /**
     * 判断当前用户是否是该公司的职位发布者
     * @param userId,workId
     * @return
     */
    @Override
    public boolean judgePriByUserId(Integer userId,Integer workId) {

        return userCompanyMapper.judgePriByUserId(userId,workId) > 0;
    }
}

