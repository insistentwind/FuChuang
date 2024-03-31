package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.CompanyEmployee;
import com.ning.mapper.CompanyEmployeeMapper;
import com.ning.service.CompanyEmployeeService;
import org.springframework.stereotype.Service;

/**
 * (CompanyUser)表服务实现类
 *
 * @author makejava
 * @since 2024-03-31 16:55:21
 */
@Service("companyEmployeeService")
public class CompanyEmployeeServiceImpl extends ServiceImpl<CompanyEmployeeMapper, CompanyEmployee> implements CompanyEmployeeService {

}

