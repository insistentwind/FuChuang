package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.MessageConstant;
import com.ning.constants.MqConstants;
import com.ning.constants.SystemConstants;
import com.ning.domain.vo.ResumeVo;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.*;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.exception.BaseException;
import com.ning.mapper.ResumeMapper;
import com.ning.mapper.UserMapper;
import com.ning.service.*;
import com.ning.utils.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2024-01-16 12:09:23
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserResumeService userResumeService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private KdfUtils kdfUtils;
    @Autowired
    private WorkUserService workUserService;
    @Autowired
    private ResumeMapper resumeMapper;
    @Autowired
    private WorkService workService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private GetResumeInfoUtils getResumeInfoUtils;

    /**
     * 用户登录接口
     *
     * @param user
     * @return
     */
    @Override
    public Result<UserVo> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        //security会自动调用这里面的userdetailsservice方法进行登录校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //TODO 没有进行到这一步
//        System.err.println(authenticate);
        //TODO 看是账号不存在还是密码错误
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new BaseException(MessageConstant.PLEASE_CHECK);
        }
        //获取用户id生成jwt存入到token
        UserDto userDto = (UserDto) authenticate.getPrincipal();
        int userId = userDto.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(userId));
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.USER_LOGIN + userId, userDto);
        //把token和userinfo封装 返回

        user = userDto.getUser();
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        userVo.setJwt(jwt);
        return Result.success(userVo);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public Result<String> register(User user) {
        //验证邮箱等个人信息
        if (!StringUtils.hasText(user.getUsername()) ||
                !StringUtils.hasText(user.getPassword()) ||
                !StringUtils.hasText(user.getMail())
//                !StringUtils.hasText(user.getName()) ||
//                !StringUtils.hasText(user.getIdCard()) ||
//                !StringUtils.hasText(user.getTele())
        ) {
            //用户名，昵称，邮箱，密码
            throw new BaseException(MessageConstant.PERSONAL_MSG_NOT_COMPLETE);
        }
        if (usernameExist(user.getUsername())) {
            System.out.println(user.getUsername());
            //账号已存在
            throw new BaseException(MessageConstant.ACCOUNT_ALREADY_EXISTS);
        }
//        else if(nameExist(user.getName())){
//            //用户名已存在
//            throw new Exception(MessageConstant.NAME_ALREADY_EXISTS);
//        }
        if (!StringUtils.hasText(user.getName())) {
            String name = "fuchuang" + UUID.randomUUID().toString().substring(0, 16);
            user.setName(name);
        }
        //密码加密
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
//        user.setCreateTime(LocalDateTime.now());
        if (user.getIsCompany() == null) {
            user.setIsCompany(0);
        }
        save(user);
        // 用户注册成功，创建一个该用户对应的观察者类
//        ObserverGenerate.generate(user.getUsername());
        return Result.success("注册成功");
    }

    /**
     * 获取当前用户默认的简历信息
     *
     * @return
     */
    @Override
    public Result<ResumeVo> getReusme() {
        User user = null;
        try {
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userDto.getUser();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }

        LambdaQueryWrapper<UserResume> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(UserResume::getUserId, user.getId())
                .eq(UserResume::getIsDefault, SystemConstants.IS_DEFAULT_RESUME);
        Resume resume = null;
        try {
            UserResume userResume = userResumeService.getOne(wrapper1);

            resume = resumeService.getById(userResume.getResumeId());
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_HAS_NO_DEFAULT_RESUME);
        }
        ResumeVo decodeResume = getDecodeResume(resume, user.getId());
//        ResumeVo resumeVo = BeanCopyUtils.copyBean(resume, ResumeVo.class);
        return Result.success(decodeResume);
    }

    /**
     * 修改当前用户的信息
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public Result<String> updateByUser(UserVo user) {
        Integer id;
        User nowUser;
        try {
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            nowUser = userDto.getUser();
            id = nowUser.getId();

        } catch (Exception e) {
            throw new BaseException("当前用户信息为空,请重试");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        if (StringUtils.hasText(user.getPassword())) {
            String oldPass = nowUser.getPassword();
            String oldPassword = user.getOldPassword();

            if (!oldPass.equals(oldPassword)) throw new BaseException("密码校验错误，请检查后重试");

            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        update(BeanCopyUtils.copyBean(user, User.class), wrapper);
        return Result.success("修改成功");
    }

    /**
     * 用户注销
     *
     * @return
     */
    @Override
    public Result<String> logout() {
        //获取token并解析获得user信息
        //通过springsecurity获取，每个线程中只保存其自己的信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authentication.getPrincipal();
        //获取userId
        Integer userId = userDto.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.USER_LOGIN + userId);
        return Result.success();
    }

    /**
     * 分页条件查询用户信息
     *
     * @param userPageVo
     * @return
     */
    @Override
    public Result<PageResult> pageByUserPageVo(UserPageVo userPageVo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userPageVo.getTele()), User::getTele, userPageVo.getTele())
                .like(StringUtils.hasText(userPageVo.getUsername()), User::getUsername, userPageVo.getUsername())
                .eq(StringUtils.hasText(userPageVo.getStatus()), User::getStatus, userPageVo.getStatus());
        wrapper.orderByDesc(User::getIsCompany);
        Page<User> page = new Page<>(userPageVo.getPageNum(), userPageVo.getPageSize());
        page(page, wrapper);
//        List<User> records = page.getRecords();
        List<AdminVo> records = BeanCopyUtils.copyBeanList(page.getRecords(), AdminVo.class);
        return Result.success(new PageResult(records.size(), records));
    }

    /**
     * 新增用户
     *
     * @param userRoleVo
     * @return
     */
    @Override
    public Result<String> insertByUserRoleVo(UserRoleVo userRoleVo) {

        //​	需要新增用户功能。新增用户时可以直接关联角色。
        //​	注意：新增用户时注意密码加密存储。
        String password = passwordEncoder.encode(userRoleVo.getPassword());
        //​	用户名不能为空，否则提示：必需填写用户名
        if (userRoleVo.getUsername().isEmpty()) {
            throw new BaseException("错误，必须填写用户名");
        }
        //​	用户名必须之前未存在，否则提示：用户名已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userRoleVo.getUsername());
        User one = getOne(wrapper);
        if (one != null) {
            throw new BaseException("新增失败，用户名已存在");
        }
        //​	邮箱必须之前未存在，否则提示：邮箱已存在
        LambdaQueryWrapper<User> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(User::getMail, userRoleVo.getMail());
        User third = getOne(wrapper3);
        if (third != null) {
            throw new BaseException("新增失败，邮箱已存在");
        }
        User user = BeanCopyUtils.copyBean(userRoleVo, User.class);
        user.setPassword(password)
                .setIsCompany(SystemConstants.IS_ADMIN);
        save(user);
        Integer userId = user.getId();
        List<Integer> roleIds = userRoleVo.getRoleIds();
        roleIds.forEach(item -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(userId).setRoleId(item);
            userRoleService.save(userRole);
        });
        return Result.success();
    }

    /**
     * 删除固定的某个用户（逻辑删除）
     *
     * @param ids
     * @return
     */
    @Override
    public Result<String> deleteById(List<Long> ids) {
        ids.forEach(this::removeById);
        return Result.success("删除成功");
    }

    /**
     * 用户信息回显
     *
     * @return
     */
    @Override
    public Result<UserVo> getInfo() {
        try {
            Integer userId = SecurityUtils.getUserId();
            User user = getById(userId);
            user.setPassword(null);
            return Result.success(BeanCopyUtils.copyBean(user, UserVo.class));
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 查询当前用户的投递历史
     *
     * @return
     */
    @Override
    public Result<List<DeliverVo>> getDliverHistory() {
        try {
            Integer userId = SecurityUtils.getUserId();
//            LambdaQueryWrapper<Deliver> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Deliver::getUserId, userId);
//            List<Deliver> list = deliverService.list(wrapper);

            LambdaQueryWrapper<WorkUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(WorkUser::getUserId, userId);
            List<WorkUser> list = workUserService.list(wrapper);

            List<DeliverVo> collect = list.stream().map(
                            item -> BeanCopyUtils.copyBean(
                                    workService.getById(item.getWorkId()), DeliverVo.class))
                    .collect(Collectors.toList());

            return Result.success(collect);
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
    }


    /**
     * 插入用户简历数据
     * TODO 美中不足的是每次插入不管用户有没有密钥，都会重新创建一份新的密钥
     * @param resumeVo
     * @return
     */
    @Override
    public Result<String> insertResume(ResumeVo resumeVo) {
        User user = null;
        try {
            user = SecurityUtils.getLoginUser().getUser();
        } catch (Exception e) {
            throw new BaseException("用户未登录");
        }
        //这里是检验用户是否已投递过简历
//        LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(UserResume::getUserId,userId);
//        if(userResumeService.getOne(wrapper) != null){
//            throw new BaseException(SystemConstants.USER_HAS_DILIVER_RESUME);
//        }
        if (resumeVo.getEmail() == null || resumeVo.getName() == null || resumeVo.getLive() == null
        || resumeVo.getTel() == null){
            throw new BaseException(SystemConstants.PLEASE_CHECK_RESUME);
        }

        Resume resume = BeanCopyUtils.copyBean(resumeVo, Resume.class);


        String name = resume.getName();
        String email = resume.getEmail();
        String tel = resume.getTel();
        String live = resume.getLive();
        try {
            SecretKeySpec key = kdfUtils.generateKey(null, null, 512);
            //用户密钥后期可以变更,因为会被存储
            SecretKeySpec userKey = kdfUtils.generateKey(user.getUsername(), null, 512);
//                        System.out.println("通过(userId" + user.getId() +  ")信息拿到的密钥:" + kdfUtils.keyToString(userKey));
            //结合密钥
            SecretKey combinedKey = kdfUtils.generateCombinedKey(key, userKey);
//                        System.out.println("整合后的密钥:" + kdfUtils.keyToString(combinedKey));
            System.out.println();
            UserKey userKey1 = new UserKey();
            userKey1.setUserId(user.getId());
            String msg = kdfUtils.keyToString(userKey);
            userKey1.setSecretKey(msg);

            rabbitTemplate.convertAndSend(MqConstants.FUCHUANG_EXCHANGE,
                    MqConstants.FUCHUANG_INSERT_KEY,userKey1);

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
            throw new BaseException("加密时出现错误!");
        }

        Integer finalUserId = user.getId();
        //开启事务
        transactionTemplate.execute(status -> {
            resumeService.save(resume);

            UserResume userResume = new UserResume();
            userResume.setResumeId(resume.getId())
                    .setUserId(finalUserId);

            userResumeService.save(userResume);
            return status;
        });

        return Result.success("操作成功");
    }

    /**
     * 简历数据修改
     *
     * @param resumeVo
     * @return
     */
    @Override
    public Result<String> resumeModify(ResumeVo resumeVo) {
        Integer userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
//        ResumeVo oldResume = resumeMapper.getInfoByUserId(userId);
        Resume resume = BeanCopyUtils.copyBean(resumeVo, Resume.class);
        LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserResume::getUserId, userId);
        UserResume userResume = userResumeService.getOne(wrapper);
        if (userResume == null) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }
        resume.setId(userResume.getResumeId());
        //加密
        resume = getResumeInfoUtils.setResumeByKey(userId, resume);

        resumeMapper.updateById(resume);
        return Result.success("修改成功");
    }

    /**
     * 设置为默认简历
     *
     * @param resumeId
     * @return
     */
    @Override
    @Transactional
    public Result<String> setDefaultResume(Integer resumeId) {
        Integer userId = null;
        LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
        UserResume userResume = null;
        try {
            userId = SecurityUtils.getUserId();

            wrapper.eq(UserResume::getUserId, userId)
                    .eq(UserResume::getResumeId, resumeId);
            userResume = userResumeService.getOne(wrapper);

            if (userResume == null) {
                return Result.error(SystemConstants.USER_HAS_NO_RESUME);
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        wrapper.clear();
        wrapper.eq(UserResume::getUserId, userId)
                .eq(UserResume::getIsDefault, SystemConstants.IS_DEFAULT_RESUME);
        UserResume oldDefault = userResumeService.getOne(wrapper);
        if (oldDefault != null) {
            oldDefault.setIsDefault(SystemConstants.IS_NOT_DEFAULT_RESUME);

            userResumeService.updateById(oldDefault);
        }

        userResume.setIsDefault(SystemConstants.IS_DEFAULT_RESUME);

        userResumeService.updateById(userResume);

        return Result.success("修改成功");
    }

    /**
     * 当前用户所创建的简历列表
     *
     * @return
     */
    @Override
    public Result<List<ResumeVo>> getResumeList() {
        User user = null;
        try {
            UserDto userDto = SecurityUtils.getLoginUser();
            user = userDto.getUser();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }

        List<ResumeVo> resumeList = userResumeService.getListByUserId(user.getId());

        if (resumeList.size() > 0) {
            User finalUser = user;

            List<Resume> collect = resumeList.stream().map(item -> {
                Resume resume = BeanCopyUtils.copyBean(item, Resume.class);
                try {
                    //密钥解析
                    Resume resumeVoByKey = getResumeInfoUtils.getResumeVoByKey(finalUser.getId(), resume);
                    return resumeVoByKey;
                } catch (Exception e) {
                    throw new BaseException(SystemConstants.HAS_NO_KEY);
                }
            }).collect(Collectors.toList());

            List<ResumeVo> resumeVos = BeanCopyUtils.copyBeanList(collect, ResumeVo.class);
            return Result.success(resumeVos);
        }
        return Result.error(SystemConstants.USER_HAS_NO_RESUME);
    }

    /**
     * 批量创建简历
     *
     * @param resumeVos
     * @return
     */
    @Override
    @Transactional
    public Result<String> deliverBatchResumes(List<ResumeVo> resumeVos) {
        User user = null;
        try {
            user = SecurityUtils.getLoginUser().getUser();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }

        Integer userId = user.getId();

        resumeVos.forEach(item -> {
            Resume resume = BeanCopyUtils.copyBean(item, Resume.class);
            // 加密
            resume = getResumeInfoUtils.setResumeByKey(userId, resume);


            resumeMapper.insert(resume);
            Integer resumeId = resume.getId();
            UserResume userResume = UserResume.builder()
                    .userId(userId)
                    .resumeId(resumeId)
                    .build();
            userResumeService.save(userResume);
        });

        return Result.success(SystemConstants.SUCCESS);
    }

    /**
     * 根据id获取用户的简历
     *
     * @param resumeId
     * @return
     */
    @Override
    public Result<ResumeVo> getResumeById(Integer resumeId) {
        Integer userId = null;
        try {
            userId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getUserId, userId)
                    .select(UserResume::getResumeId);
            List<UserResume> list = userResumeService.list(wrapper);
            List<Integer> resumeIds = list.stream().map(UserResume::getResumeId).collect(Collectors.toList());

            if (!resumeIds.contains(resumeId)) {
                return Result.error(SystemConstants.USER_HAS_NO_RESUME);
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }

        Resume resume = resumeMapper.selectById(resumeId);
        //解密Vo
        ResumeVo decodeResume = getDecodeResume(resume, userId);
        return Result.success(decodeResume);
    }

    /**
     * 密钥解析简历数据
     * @param resume
     * @param userId
     * @return
     */
    private ResumeVo getDecodeResume(Resume resume,Integer userId){
        try {
            resume = getResumeInfoUtils.getResumeVoByKey(userId, resume);
        } catch (Exception e) {
            throw new BaseException(SystemConstants.CANT_BE_ANALYZED);
        }
        ResumeVo resumeVo = BeanCopyUtils.copyBean(resume, ResumeVo.class);
        return resumeVo;
    }
    /**
     * 根据id查询用户信息回显接口
     *
     * @return
     */
    @Override
    public Result<UserRoleInfoVo> getUserInfoById(Integer id) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoleList = userRoleService.list(wrapper);
        List<String> roleIds = userRoleList.stream().map(item -> item.getRoleId().toString()).collect(Collectors.toList());

        User user = getById(id);

        List<Role> roles = roleService.list();

        UserRoleInfoVo userRoleInfoVo = new UserRoleInfoVo();

        return Result.success(userRoleInfoVo.setUser(user).setRoles(roles).setRoleIds(roleIds));
    }

    /**
     * 更新用户信息接口
     *
     * @param userRoleVo
     * @return
     */
    @Override
    public Result<String> updateUserRoleVo(UserRoleVo userRoleVo) {
        User user = BeanCopyUtils.copyBean(userRoleVo, User.class);
        updateById(user);
        //先删除userRole表的id映射关系
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleService.remove(wrapper);

        List<Integer> roleIds = userRoleVo.getRoleIds();
        roleIds.forEach(item -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId())
                    .setRoleId(item);
            userRoleService.save(userRole);
        });
        return Result.success();
    }

    /**
     * 设置简历默认可见
     *
     * @return
     */
    @Override
    public Result<String> setResumeObscure() {
        try {
            Integer userId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getUserId, userId);
            wrapper.select(UserResume::getResumeId);
            List<UserResume> resumeIds = userResumeService.list(wrapper);

            if (resumeIds.size() > 0){
                Integer resumeId = resumeIds.get(0).getResumeId();
                Resume resume = resumeService.getById(resumeId);
                Integer status = null;
                if (Objects.equals(resume.getObscure(), SystemConstants.CAN_NOT_BE_SEEN)){
                    resume.setObscure(SystemConstants.CAN_BE_SEEN);
                    status = SystemConstants.CAN_BE_SEEN;
                }
                else {
                    resume.setObscure(SystemConstants.CAN_NOT_BE_SEEN);
                    status = SystemConstants.CAN_NOT_BE_SEEN;
                }
                //遍历，给每一个resume设置是否可见
                Integer finalStatus = status;
//                resumeMapper.setObscureByUserId(userId,status);
                resumeIds.forEach(item -> {
                    Resume resume1 = new Resume();
                    resume1.setObscure(finalStatus)
                            .setId(item.getResumeId());
                    resumeMapper.updateById(resume1);
                });
            }
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }

        return Result.success(SystemConstants.SUCCESS);
    }


    private boolean usernameExist(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return count(wrapper) > 0;
    }

    private boolean nameExist(String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, name);
        return count(wrapper) > 0;
    }

}

