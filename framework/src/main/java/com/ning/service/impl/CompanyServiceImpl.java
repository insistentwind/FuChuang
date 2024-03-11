package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.Do.CompanyDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.Follow;
import com.ning.domain.entity.User;
import com.ning.domain.entity.UserCompany;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.systemConstants.SystemConstants;
import com.ning.domain.vo.CompanyVo;
import com.ning.mapper.CompanyMapper;
import com.ning.mapper.FollowMapper;
import com.ning.mapper.UserCompanyMapper;
import com.ning.mapper.UserMapper;
import com.ning.service.CompanyService;
import com.ning.service.FollowService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserCompanyMapper userCompanyMapper;
    @Autowired
    private FollowMapper followMapper;

    /**
     * 分页查询公司
     *
     * @param companyDto
     * @return
     */
    @Override
    public Result<PageResult> getListByDto(CompanyDto companyDto) {
        int pageSize = companyDto.getPageSize();
        int pageNum = companyDto.getPageNum();

        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        Company company = BeanCopyUtils.copyBean(companyDto, Company.class);
        if (company == null) {
            throw new RuntimeException("信息为空，请检查后重试");
        }
        //地址
//        wrapper.like(company.getAddress() != null, Company::getAddress, company.getAddress())
//                //行业
//                .like(company.getIndustry() != null, Company::getIndustry, company.getIndustry())
//                //融资
//                .like(company.getStage() != null, Company::getStage, company.getState())
//                //规模
//                .like(company.getFund() != null, Company::getFund, company.getFund());

        wrapper.like(StringUtils.hasText(companyDto.getCompanyName()),Company::getCompanyName,companyDto.getCompanyName())
                .like(StringUtils.hasText(companyDto.getAddress()),Company::getAddress,companyDto.getAddress())
                .eq(StringUtils.hasText(companyDto.getStage()),Company::getStage,companyDto.getStage())
                .like(StringUtils.hasText(companyDto.getIndustry()),Company::getIndustry,companyDto.getIndustry())
                .eq(StringUtils.hasText(companyDto.getType()),Company::getType,companyDto.getType());

//        wrapper.eq(Company::getStatus, SystemConstants.WORK_STATUS_NO);
        Page<Company> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<Company> records = page.getRecords();
        List<CompanyVo> companyVos = BeanCopyUtils.copyBeanList(records, CompanyVo.class);
        return Result.success(new PageResult(page.getRecords().size(), companyVos));
    }

    /**
     * 根据id查询公司
     *
     * @param id
     * @return
     */
    @Override
    public Result<CompanyVo> getCompanyById(Integer id) {
        Company company = getById(id);
        CompanyVo companyVo = BeanCopyUtils.copyBean(company, CompanyVo.class);
        return Result.success(companyVo);
    }

    /**
     * 更新公司的信息
     *
     * @param companyDto
     * @return
     */
    @Override
    public Result<String> updateByCompany(CompanyDto companyDto) {
        Company company = BeanCopyUtils.copyBean(companyDto, Company.class);
        updateById(company);
        return Result.success();
    }

    /**
     * 根据公司名称查询公司信息（公司名称是唯一的，只查出一条记录）
     * @param companyName
     * @return
     */
    @Override
    public Result<CompanyVo> getByCompanyName(String companyName) {
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getCompanyName,companyName);
        Company company = getOne(wrapper);
        if(company!=null){
            CompanyVo companyVo = BeanCopyUtils.copyBean(company, CompanyVo.class);
            return Result.success(companyVo);
        }
        return Result.error("没有此公司");
    }
    /**
     * 新增公司
     * @param companyDo
     * @return
     */
    @Override
    @Transactional
    public Result<String> createCompany(CompanyDo companyDo) {
        Company company = BeanCopyUtils.copyBean(companyDo, Company.class);
        company.setCreateTime(LocalDateTime.now());
        save(company);
        User user = new User();
        user.setIsCompany(SystemConstants.IS_COMPANY)
                .setCreateTime(LocalDateTime.now())
                .setName(companyDo.getNickName())
                .setMail(companyDo.getMail())
                .setSex(companyDo.getSex())
                .setTele(companyDo.getTele())
                .setUsername(companyDo.getUsername());
        String password = passwordEncoder.encode(companyDo.getPassword());
        user.setPassword(password);
        userMapper.insert(user);
        UserCompany userCompany = new UserCompany();
        userCompany.setUserId(user.getId());
        userCompany.setCompanyId(company.getId());
        userCompanyMapper.insert(userCompany);
        // redis哈希键初始化
        redisTemplate.opsForHash().put(company.getCompanyName(),"",0);
        return Result.success("创建成功");
    }
    /**
     * 删除公司
     * @param ids
     * @return
     */
    @Override
    public Result<String> deleteBatch(List<Integer> ids) {
        try {
            LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
            for (Integer id : ids) {
                wrapper.eq(Follow::getCompanyId,id);
                followMapper.delete(wrapper);
            }
            removeByIds(ids);
        }
        catch (Exception e){
            throw new RuntimeException("删除公司时出现错误");
        }
        return Result.success();
    }
}

