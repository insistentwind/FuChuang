package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.Company;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.systemConstants.SystemConstants;
import com.ning.domain.vo.CompanyVo;
import com.ning.mapper.CompanyMapper;
import com.ning.service.CompanyService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Company)表服务实现类
 *
 * @author makejava
 * @since 2024-01-15 18:09:05
 */
@Service("companyService")
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    /**
     * 分页查询公司
     * @param companyDto
     * @return
     */
    @Override
    public Result<PageResult> getListByDto(CompanyDto companyDto) {
        int pageSize = companyDto.getPageSize();
        int pageNum = companyDto.getPageNum();

        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        Company company = BeanCopyUtils.copyBean(companyDto, Company.class);
        if(company != null){
            //地址
            wrapper.like(company.getAddress() != null,Company::getAddress,company.getAddress())
                    //行业
                    .like(company.getIndustry() != null,Company::getIndustry,company.getIndustry())
                    //融资
                    .like(company.getStage() != null,Company::getStage,company.getState())
                    //规模
                    .like(company.getFund() != null, Company::getFund,company.getFund());
        }
//        wrapper.eq(Company::getStatus, SystemConstants.WORK_STATUS_NO);
        Page<Company> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);

        return Result.success(new PageResult(page.getRecords().size(),page.getRecords()));
    }
    /**
     * 根据id查询公司
     * @param id
     * @return
     */
    @Override
    public Result<CompanyVo> getCompanyById(Integer id) {
        Company company = getById(id);
        CompanyVo companyVo = BeanCopyUtils.copyBean(company, CompanyVo.class);
        return Result.success(companyVo);
    }
}

