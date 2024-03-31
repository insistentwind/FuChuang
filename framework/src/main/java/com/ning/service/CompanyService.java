package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.Do.CompanySignUpDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.Company;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;

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
    Result<String> createCompany(CompanySignUpDo companyDo);
    /**
     * 删除公司
     * @param ids
     * @return
     */
    Result<String> deleteBatch(List<Integer> ids);
    /**
     * 批量删除公司hr
     * @param ids
     * @return
     */
    Result<String> deleteByIds(List<Integer> ids);

    /**
     * 根据公司id查询此公司下所有职位投递的简历列表
     * @param
     * @return
     */
    Result<List<WorkVo>> getResumeListByCompany();

    /**
     * 根据职位id查询所有投递的简历列表
     * @param id
     * @return
     */
    Result<WorkVo> getResumeListByWorkId(Integer id);
    /**
     * 根据用户id查询此用户的简历
     * @return
     */
    Result<ResumeVo> getResumeVoByUserId(Integer userId);
    /**
     * 分页查询此公司下发布的职位
     * @param workPageVo
     * @return
     */
    Result<List<WorkVo>> pageByCategoryId(WorkPageVo workPageVo);

    /**
     *  公司端投递简历
     * @param resumeVoList
     * @return
     */
    Result<String> commitResumeList(List<ResumeVo> resumeVoList);
    /**
     * 当前账号公司所有行业
     * @return
     */
    Result<List<String>> getIndustry(String companyBrand);
//    /**
//     * 新增公司员工
//     * @param userRoleVo
//     * @return
//     */
//    Result<String> addEmployee(UserRoleVo userRoleVo);
}

