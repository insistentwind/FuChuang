package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.UserCompany;

/**
 * (UserCompany)表服务接口
 *
 * @author makejava
 * @since 2024-03-05 21:25:14
 */
public interface UserCompanyService extends IService<UserCompany> {
    /**
     * 判断当前用户是否是该公司的职位发布者
     * @param
     * @return
     */
    boolean judgePriByUserId(Integer userId,Integer workId);
}

