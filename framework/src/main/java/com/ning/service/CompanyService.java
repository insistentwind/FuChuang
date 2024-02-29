package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.Do.CompanyDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.Company;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.CompanyVo;

import java.util.List;


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
    /**
     * 根据id查询公司
     * @param id
     * @return
     */
    Result<CompanyVo> getCompanyById(Integer id);
    /**
     * 更新公司的信息
     * @param companyDto
     * @return
     */
    Result<String> updateByCompany(CompanyDto companyDto);
    /**
     * 根据公司名称查询公司信息（公司名称是唯一的，只查出一条记录）
     * @param companyName
     * @return
     */
    Result<CompanyVo> getByCompanyName(String companyName);
    /**
     * 新增公司
     * @param companyDo
     * @return
     */
    Result<String> createCompany(CompanyDo companyDo);
    /**
     * 删除公司
     * @param ids
     * @return
     */
    Result<String> deleteBatch(List<Integer> ids);
}

