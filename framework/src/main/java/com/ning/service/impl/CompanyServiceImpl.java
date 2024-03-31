package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.Do.CompanySignUpDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.*;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.exception.BaseException;
import com.ning.mapper.*;
import com.ning.service.*;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private WorkResumeMapper workResumeMapper;
    @Autowired
    private WorkUserMapper workUserMapper;
    @Autowired
    private ResumeMapper resumeMapper;
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private UserResumeMapper userResumeMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyEmployeeMapper companyEmployeeMapper;

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

        wrapper.eq(StringUtils.hasText(companyDto.getBrandIndustry()), Company::getBrandIndustry, companyDto.getBrandIndustry())
                .like(StringUtils.hasText(companyDto.getBrandName()), Company::getBrandName, companyDto.getBrandName())
                .eq(StringUtils.hasText(companyDto.getBrandScaleName()), Company::getBrandScaleName, companyDto.getBrandScaleName());

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
     * 根据公司名称查询公司信息（公司名称是唯一的，只查出一条记录）
     *
     * @param companyName
     * @return
     */
    @Override
    public Result<CompanyVo> getByCompanyName(String companyName) {
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getBrandName, companyName);
        List<Company> companyList = list(wrapper);
        if (companyList != null && companyList.size() > 0) {
            /**
             * 拿到了第一条数据
             */
            Company company = companyList.get(0);
            CompanyVo companyVo = BeanCopyUtils.copyBean(company, CompanyVo.class);
            return Result.success(companyVo);
        }
        return Result.error("没有此公司");
    }

    /**
     * 新增公司
     *
     * @param companyDo
     * @return
     */
    @Override
    @Transactional
    public Result<String> createCompany(CompanySignUpDo companyDo) {
        if(companyDo.getUsername() == null || companyDo.getPassword() == null){
            throw new BaseException("创建失败,用户名或密码不能为空");
        }
        else{
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername,companyDo.getUsername());
            User user = userMapper.selectOne(wrapper);
            if (user != null){
                throw new BaseException("创建失败，用户名已存在");
            }
        }

        Company company = BeanCopyUtils.copyBean(companyDo, Company.class);
        company.setCreateTime(LocalDateTime.now());
        save(company);

        User user = new User();

        user.setIsCompany(SystemConstants.IS_COMPANY)
                .setCreateTime(LocalDateTime.now())
                .setName(companyDo.getName())
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
        redisTemplate.opsForHash().put(company.getBrandName(), "", 0);
        return Result.success("创建成功");
    }

    /**
     * 删除公司
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Result<String> deleteBatch(List<Integer> ids) {
        try {
            User user = SecurityUtils.getLoginUser().getUser();
            if (!Objects.equals(user.getIsCompany(), SystemConstants.IS_ADMIN)){
                throw new BaseException(SystemConstants.IS_NOT_ADMIN);
            }

            ids.forEach(id ->{
                //删除跟随者
                LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Follow::getCompanyId, id);
                followMapper.delete(wrapper);

                Company company = getById(id);
                removeById(id);
                redisTemplate.opsForHash().delete(company.getBrandName());
            });

        } catch (Exception e) {
            throw new RuntimeException("删除公司时出现错误");
        }
        return Result.success();
    }
    /**
     * 批量删除公司hr
     * @param ids
     * @return
     */
    @Override
    public Result<String> deleteByIds(List<Integer> ids) {
        removeBatchByIds(ids);
        return Result.success("删除成功");
    }

    /**
     * 查询此公司下所有职位投递的简历列表
     * @param
     * @return
     */
    @Override
    public Result<List<WorkVo>> getResumeListByCompany() {
        Integer userId = null;
        try {
            UserDto loginUser = SecurityUtils.getLoginUser();
            User user = loginUser.getUser();
            userId = user.getId();
        }
        catch (Exception e){
            throw new BaseException("用户当前未登录");
        }

        Integer companyId = userCompanyMapper.getCompanyIdByUserId(userId);

        if (companyId == null){
            throw new BaseException("没有此公司");
        }
//        else {
//            try {
//                //判断当前用户是否是与此公司关联
//                UserCompany userCompany = judgeUserMatchedCompany(company.getId());
//                if(userCompany == null){
//                    throw new BaseException(SystemConstants.HAS_NO_MATCHED_USER_COMPANY);
//                }
//            }
//            catch (Exception e){
//                throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
//            }
//        }

        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Relation::getCompanyId,companyId);
        List<Relation> companyWorks = relationMapper.selectList(wrapper);
        // 第一次 职位id的list
        List<WorkVo> workVoList = companyWorks.stream().map(
                item -> getWorkResumeList(item.getWorkId()))
                .collect(Collectors.toList());

        return Result.success(workVoList);
    }

    private WorkVo getWorkResumeList(Integer workId){
        // 第二次 这里拿到了简历id的list
        LambdaQueryWrapper<WorkUser> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(WorkUser::getWorkId, workId);
        List<WorkUser> workUsers = workUserMapper.selectList(wrapper1);
        // 第三次 要拿根据简历id拿到所有简历的内容
        List<ResumeVo> ResumeList = workUsers.stream().map(o -> {
            Integer resumeId = o.getResumeId();
            Resume resume = resumeMapper.selectById(resumeId);
            return BeanCopyUtils.copyBean(resume,ResumeVo.class);
        }).collect(Collectors.toList());

        Work work = workMapper.selectById(workId);
        WorkVo workVo = BeanCopyUtils.copyBean(work, WorkVo.class);
        workVo.setResumeList(ResumeList);
        return workVo;
    }
    //参数是公司id
    private UserCompany judgeUserMatchedCompany(Integer companyId){
        Integer userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCompany::getUserId,userId)
                .eq(UserCompany::getCompanyId,companyId);
        UserCompany userCompany = userCompanyMapper.selectOne(wrapper);
        return userCompany;
    }
    /**
     * 根据职位id查询所有投递的简历列表
     * @param id
     * @return
     */
    @Override
    public Result<WorkVo> getResumeListByWorkId(Integer id) {
        try {
            LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Relation::getWorkId,id);
            Relation relation = relationMapper.selectOne(wrapper);
            UserCompany userCompany = judgeUserMatchedCompany(relation.getCompanyId());
            if(userCompany == null){
                throw new BaseException(SystemConstants.HAS_NO_MATCHED_USER_COMPANY);
            }
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        // 第二次 这里拿到了简历id的list
        return Result.success(getWorkResumeList(id));
    }

    /**
     * 根据用户id查询此用户的简历
     * @return
     */
    @Override
    public Result<ResumeVo> getResumeVoByUserId(Integer userId) {
        Integer companyId;
        // 拿到了当前操作者所属的公司id
        try {
            Integer hrId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId,hrId);
            UserCompany userCompany = userCompanyMapper.selectOne(wrapper);
            companyId = userCompany.getCompanyId();
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        try {
            // 根据用户找到当前所属公司
            // 通过公司id查询发布的所有职位
            /**
             * SELECT * FROM relation re
             * LEFT JOIN work w ON re.work_id = w.id
             * LEFT JOIN work_user wu ON w.id = wu.work_id
             * WHERE wu.user_id = 12 AND re.company_id = 3;
             */
            if(relationMapper.getUserByCompany(companyId,userId) > 0){
                //说明当前用户已经投递简历，直接回显即可
                ResumeVo resumeVo = resumeMapper.getInfoByUserId(userId);
                resumeVo.setUserId(userId);
                return Result.success(resumeVo);
            }
            // 通过职位找到当前用户对应的简历id
            throw new BaseException(SystemConstants.USER_NO_PERMITED);

        } catch (Exception e) {
            throw new BaseException(e.toString());
        }
    }
    /**
     * 条件查询此公司下发布的职位
     * @param workPageVo
     * @return
     */
    @Override
    public Result<List<WorkVo>> pageByCategoryId(WorkPageVo workPageVo) {
        User user = null;
        try {
            UserDto loginUser = SecurityUtils.getLoginUser();
            user = loginUser.getUser();
            if (user.getIsCompany() == SystemConstants.IS_NOT_COMPANY){
                return Result.error("当前用户没有权限查询发布职位");
            }
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        Integer companyId = null;
        try {
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId,user.getId());
            UserCompany userCompany = userCompanyMapper.selectOne(wrapper);
            companyId = userCompany.getCompanyId();
            if (companyId == null){
                return Result.error(SystemConstants.HAS_NO_MATCHED_USER_COMPANY);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("出现未知错误");
        }
        workPageVo.setCompanyId(companyId);
        List<Work> workList = relationMapper.getWorkByCompanyId(companyId);
//        List<Work> workList = relationMapper.getWorkByCompanyId(companyId);
        if (workList.size() > 0 && workList != null){
            List<WorkVo> workVos = BeanCopyUtils.copyBeanList(workList, WorkVo.class);
            return Result.success(workVos);
        }
        else {
            return Result.error(SystemConstants.COMPANY_HAS_NO_POSITION);
        }
//        Integer pageSize = workPageVo.getPageSize();
//        Integer pageNum = workPageVo.getPageNum();
//        Page<Work> page = new Page<>(pageNum,pageSize);
//        LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(workPageVo.getCategoryId() != null,Work::getClassifyId,workPageVo.getCategoryId());
//        Page<Work> workPage = workMapper.selectPage(page, wrapper);
//        List<Work> records = workPage.getRecords();
//        return Result.success(new PageResult(records.size(),records));
    }
    /**
     *  公司端投递简历
     * @param resumeVoList
     * @return
     */
    @Override
    @Transactional
    public Result<String> commitResumeList(List<ResumeVo> resumeVoList) {
        User user = getUser();
        Integer userId = user.getId();
        resumeVoList.forEach(item -> {
            Resume resume = BeanCopyUtils.copyBean(item, Resume.class);
            resumeMapper.insert(resume);
            Integer resumeId = resume.getId();
            UserResume userResume = UserResume.builder().resumeId(resumeId)
                    .userId(userId)
                    .build();
            userResumeMapper.insert(userResume);
            //简历创建好后，把简历投递到自己公司
            WorkUser workUser = WorkUser.builder().workId(item.getWorkId())
                    .resumeId(resumeId)
                    .userId(userId)
                    .build();
            workUserMapper.insert(workUser);
        });

        return Result.success(SystemConstants.SUCCESS);
    }
    /**
     * 根据公司名查询所有行业
     * @return
     */
    @Override
    public Result<List<String>> getIndustry(String companyBrand) {
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getBrandName,companyBrand)
                        .select(Company::getBrandIndustry);
        List<Company> companies = companyMapper.selectList(wrapper);
        List<String> collect = companies.stream().map(Company::getBrandIndustry).collect(Collectors.toList());
        return Result.success(collect);
    }
//    /**
//     * 新增公司员工
//     * @param userRoleVo
//     * @return
//     */
//    @Override
//    public Result<String> addEmployee(UserRoleVo userRoleVo) {
//        User user = getUser();
//        if (!Objects.equals(user.getIsCompany(), SystemConstants.IS_COMPANY)){
//            return Result.error(SystemConstants.NOW_USER_IS_NOT_COMPANY);
//        }
//        Integer companyId = userCompanyMapper.getCompanyIdByUserId(user.getId());
//        Company company = getById(companyId);
//        createCompany()
//        CompanyEmployee companyEmployee = new CompanyEmployee();
//        companyEmployeeMapper.insert()
//        return null;
//    }

    private Integer check(Integer CompanyId) {
        Integer userId = SecurityUtils.getUserId();
        Integer count = userCompanyMapper.judgePriByUserId(userId, CompanyId);
        if (count > 0) {
            return userId;
        } else {
            throw new BaseException(SystemConstants.HAS_NO_PERMISSION);
        }
    }

    /**
     * 校验当前登录的用户
     * @return
     */
    private User getUser(){
        User user = null;
        try {
            UserDto loginUser = SecurityUtils.getLoginUser();
            user = loginUser.getUser();
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        if (user.getIsCompany() != 1){
            throw new BaseException(SystemConstants.HAS_NO_PERMISSION);
        }
        return user;
    }

    /**
     * 更新公司的信息
     *
     * @param companyDto
     * @return
     */
    @Override
    public Result<String> updateByCompany(CompanyDto companyDto) {
        check(companyDto.getId());
        Company company = BeanCopyUtils.copyBean(companyDto, Company.class);
        updateById(company);
        return Result.success();
    }
}

