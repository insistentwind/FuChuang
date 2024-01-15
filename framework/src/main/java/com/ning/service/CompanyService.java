package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.Company;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;


/**
 * (Company)表服务接口
 *
 * @author makejava
 * @since 2024-01-15 18:09:04
 */
public interface CompanyService extends IService<Company> {
    /**
     * 分页查询所有公司
     * @param companyDto
     * @return
     */
    Result<PageResult> getListByDto(CompanyDto companyDto);
}

