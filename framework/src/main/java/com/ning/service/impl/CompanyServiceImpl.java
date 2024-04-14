package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.MqConstants;
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
import com.ning.utils.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
    @Autowired
    private GetResumeInfoUtils getResumeInfoUtils;
    @Autowired
    private UserPermitcompanyService userPermitcompanyService;
    @Autowired
    private AckService ackService;
    @Autowired
    private KdfUtils kdfUtils;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private WorkUserService workUserService;

    /**
     * 分页查询公司
     *
     * @param companyDto
     * @return
     */
    @Override
    public Result<PageResult> getListByDto(CompanyDto companyDto) {
        Integer pageSize = null;
        Integer pageNum = null;
        try {
            pageSize = companyDto.getPageSize();
            pageNum = companyDto.getPageNum();
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.CHECK_INPUT);
        }

        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        Company company = BeanCopyUtils.copyBean(companyDto, Company.class);
        if (company == null) {
            throw new RuntimeException("信息为空，请检查后重试");
        }

        wrapper.eq(Company::getStatus,SystemConstants.COMPANY_CHECK_PASS);

        wrapper.eq(StringUtils.hasText(companyDto.getBrandIndustry()), Company::getBrandIndustry, companyDto.getBrandIndustry())
                .like(StringUtils.hasText(companyDto.getBrandName()), Company::getBrandName, companyDto.getBrandName())
                .eq(StringUtils.hasText(companyDto.getBrandScaleName()), Company::getBrandScaleName, companyDto.getBrandScaleName());

//        wrapper.eq(Company::getStatus, SystemConstants.WORK_STATUS_NO);
        Page<Company> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<Company> records = page.getRecords();
        List<CompanyVo> companyVos = BeanCopyUtils.copyBeanList(records, CompanyVo.class);
        return Result.success(new PageResult((int) page.getTotal(), companyVos));
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
        if(company == null){
            throw new BaseException(SystemConstants.HAS_NO_COMPANY);
        }
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
        if (companyDo.getUsername() == null || companyDo.getPassword() == null) {
            throw new BaseException("创建失败,用户名或密码不能为空");
        } else {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, companyDo.getUsername());
            User user = userMapper.selectOne(wrapper);
            if (user != null) {
                throw new BaseException("创建失败，用户名已存在");
            }
        }

        Company company = BeanCopyUtils.copyBean(companyDo, Company.class);
        company.setCreateTime(LocalDateTime.now());
        //这里就直接保存了
        //默认是没有被审核过的
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
        //用户可以先插入，但是公司待审核
        UserCompany userCompany = new UserCompany();
        userCompany.setUserId(user.getId());
        userCompany.setCompanyId(company.getId());
        userCompanyMapper.insert(userCompany);
//        // redis哈希键初始化
//        redisTemplate.opsForHash().put(company.getBrandName(), "", 0);
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
            if (!Objects.equals(user.getIsCompany(), SystemConstants.IS_ADMIN)) {
                throw new BaseException(SystemConstants.IS_NOT_ADMIN);
            }

            ids.forEach(id -> {
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
     *
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
     *
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
        } catch (Exception e) {
            throw new BaseException("用户当前未登录");
        }

        Integer companyId = userCompanyMapper.getCompanyIdByUserId(userId);

        if (companyId == null) {
            throw new BaseException("没有此公司");
        }

        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Relation::getCompanyId, companyId);
        List<Relation> companyWorks = relationMapper.selectList(wrapper);
        // 第一次 职位id的list
        List<WorkVo> workVoList = companyWorks.stream().map(
                //公司的works 的id
                        item -> getWorkResumeList(item.getWorkId(),companyId))
                .collect(Collectors.toList());

        return Result.success(workVoList);
    }

    private WorkVo getWorkResumeList(Integer workId,Integer companyId) {
        // 第二次 这里拿到了简历id的list
        LambdaQueryWrapper<WorkUser> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(WorkUser::getWorkId, workId)
                .orderByDesc(WorkUser::getCreateTime);
        //因为一个职位会有多个用户投递简历，所以不能拿到第一个就做判断是需要的数据
        List<WorkUser> workUsers = workUserMapper.selectList(wrapper1);

        if (workUsers.size() < 1){
            return null;
        }
        // 第三次 要拿根据简历id拿到所有简历的内容
        List<ResumeVo> ResumeList = workUsers.stream().map(o -> {
            Integer resumeId = o.getResumeId();
            Integer userId = o.getUserId();
            Resume resume = resumeMapper.selectById(resumeId);
            //判断权限并拿到信息
            try {
                return selectPermsToViewResume(userId, companyId,
                        BeanCopyUtils.copyBean(resume, ResumeVo.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
//                getResumeInfoUtils.
                //TODO 解密需要拿到简历的obscure和当前用户是否允许公司查看信息
        }).collect(Collectors.toList());

        Work work = workMapper.selectById(workId);
        if (work != null){
            WorkVo workVo = BeanCopyUtils.copyBean(work, WorkVo.class);
            workVo.setResumeList(ResumeList);
            return workVo;
        }
        return null;
    }

    /**
     * 判断当前公司的用户是否匹配
     * @param companyId
     * @return
     */
    //参数是公司id
    private UserCompany judgeUserMatchedCompany(Integer companyId) {
        Integer userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCompany::getUserId, userId)
                .eq(UserCompany::getCompanyId, companyId);
        UserCompany userCompany = userCompanyMapper.selectOne(wrapper);
        return userCompany;
    }

    /**
     * 根据职位id查询所有投递的简历列表
     *
     * @param id
     * @return
     */
    @Override
    //这里id是职位的id
    public Result<WorkVo> getResumeListByWorkId(Integer id) {
        Integer companyId = null;
        try {
            LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Relation::getWorkId, id);
            Relation relation = relationMapper.selectOne(wrapper);
            //这里是relation职位对应的公司
            UserCompany userCompany = judgeUserMatchedCompany(relation.getCompanyId());
            companyId = relation.getCompanyId();
            if (userCompany == null) {
                return Result.error(SystemConstants.HAS_NO_MATCHED_USER_COMPANY);
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        // 第二次 这里拿到了简历id的list
        return Result.success(getWorkResumeList(id,companyId));
    }

    /**
     * 拿到分页内容
     * 与getWorkResumeList接口类似，应替代其作为新接口
     * 作用为查询某职位下所有的简历内容(分页查询 ！重要)
     */
//    private PageResult getWorkResumePageList(Integer workId,Integer companyId,Integer pageSize,Integer pageNum) {
//        Page<WorkUser> page = new Page<>(pageNum,pageSize);
//
//        // 第二次 这里拿到了简历id的list
//        LambdaQueryWrapper<WorkUser> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(WorkUser::getWorkId, workId)
//                .orderByDesc(WorkUser::getCreateTime);
//        Page<WorkUser> records = workUserService.page(page, wrapper1);
//        List<WorkUser> workUsers = records.getRecords();
////        List<WorkUser> workUsers = workUserMapper.selectList(wrapper1);
//        if (workUsers.size() < 1){
//            throw new BaseException(SystemConstants.WORK_HAS_NO_RESUME);
//        }
//        // 第三次 要拿根据简历id拿到所有简历的内容
//        List<ResumeVo> ResumeList = workUsers.stream().map(o -> {
//            Integer resumeId = o.getResumeId();
//            Integer userId = o.getUserId();
//            Resume resume = resumeMapper.selectById(resumeId);
//            //判断权限并拿到信息
//            try {
//                return selectPermsToViewResume(userId, companyId,
//                        BeanCopyUtils.copyBean(resume, ResumeVo.class));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
////                getResumeInfoUtils.
//        }).collect(Collectors.toList());
//
//        return new PageResult((int) page.getTotal(),ResumeList);
////        Work work = workMapper.selectById(workId);
////        if (work != null){
////            WorkVo workVo = BeanCopyUtils.copyBean(work, WorkVo.class);
////            workVo.setResumeList(ResumeList);
////            return workVo;
////        }
//    }

    /**
     * 根据用户id查询此用户的简历
     *
     * @return
     */
    @Override
    public Result<ResumeVo> getResumeVoByUserId(Integer userId) {
        Integer companyId;
        // 拿到了当前操作者所属的公司id
        try {
            Integer hrId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId, hrId);
            UserCompany userCompany = userCompanyMapper.selectOne(wrapper);
            companyId = userCompany.getCompanyId();
        } catch (Exception e) {
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
            if (relationMapper.getUserByCompany(companyId, userId) > 0) {
                //说明当前用户已经投递简历，直接回显即可
                List<ResumeVo> resumeVos = resumeMapper.getInfoByUserId(userId);
                ResumeVo resumeVo = resumeVos.get(0);
                resumeVo.setUserId(userId);
                resumeVo = selectPermsToViewResume(userId, companyId, resumeVo);
                return Result.success(resumeVo);
            }
            // 通过职位找到当前用户对应的简历id
            throw new BaseException(SystemConstants.USER_NO_PERMITED);

        } catch (Exception e) {
            throw new BaseException(e.toString());
        }
    }

    /**
     * 密钥审核有没有权限
     *
     * @param userId
     * @param companyId
     * @param resumeVo
     * @return
     * @throws Exception
     */
    private ResumeVo selectPermsToViewResume(Integer userId, Integer companyId, ResumeVo resumeVo) throws Exception {
        //这里开始看有没有权限
        LambdaQueryWrapper<UserPermitcompany> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(UserPermitcompany::getUserId, userId)
                .eq(UserPermitcompany::getCompanyPermitId, companyId);
        UserPermitcompany one = userPermitcompanyService.getOne(wrapper1);

        if (one != null || Objects.equals(resumeVo.getObscure(), SystemConstants.CAN_BE_SEEN)) {
            Resume resume = BeanCopyUtils.copyBean(resumeVo, Resume.class);
            //注意这里千万不要把不是这个人的简历放进来，因为会报错密钥解密不出来，错误很难找
            Resume resumeVoByKey = getResumeInfoUtils.getResumeVoByKey(userId, resume);

            resumeVo = BeanCopyUtils.copyBean(resumeVoByKey, ResumeVo.class);
        }
        return resumeVo;

    }

    /**
     * 条件查询此公司下发布的职位
     *
     * @param workPageVo
     * @return
     */
    @Override
    public Result<List<WorkVo>> pageByCategoryId(WorkPageVo workPageVo) {
        User user = null;
        try {
            UserDto loginUser = SecurityUtils.getLoginUser();
            user = loginUser.getUser();
            if (Objects.equals(user.getIsCompany(), SystemConstants.IS_NOT_COMPANY)) {
                return Result.error("当前用户没有权限查询发布职位");
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        Integer companyId = null;
        try {
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId, user.getId());
            UserCompany userCompany = userCompanyMapper.selectOne(wrapper);
            companyId = userCompany.getCompanyId();
            if (companyId == null) {
                return Result.error(SystemConstants.HAS_NO_MATCHED_USER_COMPANY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("出现未知错误");
        }
        workPageVo.setCompanyId(companyId);
        List<Work> workList = relationMapper.getWorkByCompanyId(companyId);
//        List<Work> workList = relationMapper.getWorkByCompanyId(companyId);
        if (workList.size() > 0 && workList != null) {
            List<WorkVo> workVos = BeanCopyUtils.copyBeanList(workList, WorkVo.class);
            return Result.success(workVos);
        } else {
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
     * 公司端投递简历
     *
     * @param resumeVoList
     * @return
     */
    //todo 还没有写加密
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
            //todo 拿密钥
            String name = resume.getName();
            String email = resume.getEmail();
            String tel = resume.getTel();
            String live = resume.getLive();
            try {
                //TODO 这里是创建简历时的/密钥/相关操作
                //获取初始密钥
                SecretKeySpec key = kdfUtils.generateKey(null, null, 512);
                //用户密钥后期可以变更,因为会被存储,盐值可以随机改变
                //todo 目前没有判断用户是否已经有密钥了，如果设置盐值随机，那么新创建的简历加密后的密钥会覆盖老的密钥.导致老的简历解析不出来
                //解决方法是，先从mq中把另一个服务中查看当前用户是否已经创建了密钥
                //这个是查询先查询密钥
                UserKey userKeyEntity = KeyHttpUtils.sendGetRequest(SystemConstants.KEY_CLIENT_URL, user.getId());
                SecretKeySpec userKey = null;
                if (userKeyEntity == null){
                    userKey = kdfUtils.generateKey(user.getUsername(), new byte[16], 512);
                    //创建密钥
                    UserKey userKey1 = new UserKey();
                    userKey1.setUserId(user.getId());
                    String msg = kdfUtils.keyToString(userKey);
                    userKey1.setSecretKey(msg);

                    rabbitTemplate.convertAndSend(MqConstants.FUCHUANG_EXCHANGE,
                            MqConstants.FUCHUANG_INSERT_KEY,userKey1);
                }
                else {
                    //否则，密钥存在，直接拿到密钥
                    String secretKey = userKeyEntity.getSecretKey();
                    userKey = kdfUtils.stringToKey(secretKey);
                }
                //结合密钥
                SecretKey combinedKey = kdfUtils.generateCombinedKey(key, userKey);


                /**
                 * 获取密钥的方式都相同
                 */
                String username = kdfUtils.Encoding(name, combinedKey);
                String codeMail = kdfUtils.Encoding(email,combinedKey);
                String codeTel = kdfUtils.Encoding(tel,combinedKey);
                String codeLive = kdfUtils.Encoding(live, combinedKey);
                resume.setName(username)
                        .setEmail(codeMail)
                        .setTel(codeTel)
                        .setLive(codeLive);

//            resumeService.updateById(resume);

            } catch (Exception e) {
                e.printStackTrace();
                throw new BaseException("加密时出现错误!请检查字段是否填写完整");
            }


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
     *
     * @return
     */
    @Override
    public Result<List<String>> getIndustry(String companyBrand) {
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getBrandName, companyBrand)
                .select(Company::getBrandIndustry);
        List<Company> companies = companyMapper.selectList(wrapper);
        List<String> collect = companies.stream().map(Company::getBrandIndustry).collect(Collectors.toList());
        return Result.success(collect);
    }

    /**
     * 据id查询简历
     *
     * @return TODO 压力最大的一个接口
     */
    @Override
    public Result<ResumeVo> getResumeVoByResumeId(Integer resumeId) {
        Integer companyId = null;
        // 拿到了当前操作者所属的公司id
        try {
            Integer hrId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserCompany> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(UserCompany::getUserId, hrId);
            UserCompany userCompany = userCompanyMapper.selectOne(wrapper1);
            companyId = userCompany.getCompanyId();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        try {

            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getResumeId, resumeId);
            UserResume userResume = userResumeMapper.selectOne(wrapper);
            Integer userId = userResume.getUserId();
            // 根据用户找到当前所属公司
            // 通过公司id查询发布的所有职位
            /**
             * SELECT * FROM relation re
             * LEFT JOIN work w ON re.work_id = w.id
             * LEFT JOIN work_user wu ON w.id = wu.work_id
             * WHERE wu.user_id = 12 AND re.company_id = 3;
             */
            //这个找到了当前公司和用户的对应关系
            Integer userByCompany = relationMapper.getUserByCompany(companyId, userId);
            if (userByCompany > 0) {
                //说明当前用户已经投递简历，直接回显即可
                List<ResumeVo> resumeVos = resumeMapper.getInfoByUserId(userId);
                //todo 与下面关联 根据用户id查询此用户的简历
                ResumeVo resumeVo = resumeVos.get(0);

                resumeVo.setUserId(userId);
                //todo 如果用户允许才能回显所有的数据
                resumeVo = selectPermsToViewResume(userId, companyId, resumeVo);
                return Result.success(resumeVo);
            }
            // 通过职位找到当前用户对应的简历id
            return Result.error(SystemConstants.USER_NO_PERMITED);

        } catch (Exception e) {
            throw new BaseException(e.toString());
        }

    }

    /**
     * 请求查看用户简历
     *
     * @param userId
     * @return
     */
    @Override
    public Result<String> sendRequestToUser(Integer userId) {
        Integer companyId = null;
        // 拿到了当前操作者所属的公司id
        try {
            Integer hrId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserCompany> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(UserCompany::getUserId, hrId);
            UserCompany userCompany = userCompanyMapper.selectOne(wrapper1);
            companyId = userCompany.getCompanyId();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        Company company = companyMapper.selectById(companyId);

        LambdaQueryWrapper<Ack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Ack::getCompanyId,company.getId())
                        .eq(Ack::getUserId,userId)
                                .eq(Ack::getIsCompany,SystemConstants.IS_COMPANY);
        Ack ack = ackService.getOne(wrapper);
        if (ack == null){
            ack = new Ack();
            ack.setCompanyId(companyId)
                    .setUserId(userId)
                    .setCompanyName(company.getBrandName())
                    .setUsername(userService.getById(userId).getName())
                    .setIsCompany(SystemConstants.IS_COMPANY)
                    .setIsRead(SystemConstants.HAS_NO_READ)
                    .setTime(LocalDateTime.now())
                    .setContent(company.getBrandName() + "公司希望查看您的简历");
            ackService.save(ack);
        }
        else {
            //否则更新这条信息
            ack.setCompanyId(companyId)
                    .setUserId(userId)
                    .setCompanyName(company.getBrandName())
                    .setUsername(userService.getById(userId).getName())
                    .setIsCompany(SystemConstants.IS_COMPANY)
                    .setIsRead(SystemConstants.HAS_NO_READ)
                    .setTime(LocalDateTime.now())
                    .setContent(company.getBrandName() + "公司希望查看您的简历");
            ackService.updateById(ack);
        }

        return Result.success(SystemConstants.SUCCESS);
    }
    /**
     * 条件查询公司下发布的职位
     * @param workPageVo
     * @return
     */
    @Override
    public Result<List<WorkVo>> pageUserClientByCategoryId(WorkPageVo workPageVo) {
        if(workPageVo == null){
            throw new BaseException(SystemConstants.PARAMS_MUST_NOT_BE_NULL);
        }
        List<Work> workList = relationMapper.getWorkByCategory(workPageVo);
//        List<Work> workList = relationMapper.getWorkByCompanyId(companyId);
        if (workList.size() > 0 && workList != null) {
            List<WorkVo> workVos = BeanCopyUtils.copyBeanList(workList, WorkVo.class);
            return Result.success(workVos);
        } else {
            return Result.error(SystemConstants.COMPANY_HAS_NO_POSITION);
        }
    }
    /**
     * 分页查询待审核公司
     * @param companyDto
     * @return
     */
    @Override
    public Result<PageResult> getStatusList(CompanyDto companyDto) {
        Integer pageSize = null;
        Integer pageNum = null;
        try {
            pageSize = companyDto.getPageSize();
            pageNum = companyDto.getPageNum();
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.CHECK_INPUT);
        }

        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        Company company = BeanCopyUtils.copyBean(companyDto, Company.class);
        if (company == null) {
            throw new RuntimeException("信息为空，请检查后重试");
        }


        wrapper.eq(StringUtils.hasText(companyDto.getBrandIndustry()), Company::getBrandIndustry, companyDto.getBrandIndustry())
                .like(StringUtils.hasText(companyDto.getBrandName()), Company::getBrandName, companyDto.getBrandName())
                .eq(StringUtils.hasText(companyDto.getBrandScaleName()), Company::getBrandScaleName, companyDto.getBrandScaleName())
                .eq(companyDto.getStatus() != null,Company::getStatus, companyDto.getStatus());

//        wrapper.eq(Company::getStatus, SystemConstants.WORK_STATUS_NO);
        Page<Company> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<Company> records = page.getRecords();
        List<CompanyVo> companyVos = BeanCopyUtils.copyBeanList(records, CompanyVo.class);
        return Result.success(new PageResult((int) page.getTotal(), companyVos));
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
     *
     * @return
     */
    private User getUser() {
        User user = null;
        try {
            UserDto loginUser = SecurityUtils.getLoginUser();
            user = loginUser.getUser();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        if (user.getIsCompany() != 1) {
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

