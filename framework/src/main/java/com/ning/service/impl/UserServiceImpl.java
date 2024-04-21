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
import org.springframework.beans.BeanUtils;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
        /**
         * 这里如果有简历，那么就说明是一定存在密钥的
         */
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

        Page<User> page = new Page<>(userPageVo.getPageNum(), userPageVo.getPageSize());
        page(page, wrapper);
//        List<User> records = page.getRecords();
        List<AdminVo> records = BeanCopyUtils.copyBeanList(page.getRecords(), AdminVo.class);
        return Result.success(new PageResult((int) page.getTotal(), records));
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
        List<Long> roleIds = userRoleVo.getRoleIds();
        roleIds.forEach(item -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(Long.valueOf(userId)).setRoleId(item);
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

            List<DeliverVo> collect = list.stream().map(item -> {
                        Work work = workService.getById(item.getWorkId());
                        return DeliverVo.builder()
                                .title(work.getTitle())
                                .workId(work.getId())
                                .salary(work.getSalaryDesc())
                                .education(work.getEducation().toString())
                                .viewCount(work.getViewCount())
                                .build();
                    })
                    .collect(Collectors.toList());

            return Result.success(collect);
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
    }


    /**
     * 插入用户简历数据
     * TODO 美中不足的是每次插入不管用户有没有密钥，都会重新创建一份新的密钥
     *
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
        if (resumeVo.getEmail() == null || resumeVo.getName() == null
                || resumeVo.getTel() == null) {
            throw new BaseException(SystemConstants.PLEASE_CHECK_RESUME);
        }
        if (resumeVo.getLive() == null) {
            resumeVo.setLive("火星");
        }

        Resume resume = BeanCopyUtils.copyBean(resumeVo, Resume.class);


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
            if (userKeyEntity == null) {
                userKey = kdfUtils.generateKey(user.getUsername(), new byte[16], 512);
                //创建密钥
                UserKey userKey1 = new UserKey();
                userKey1.setUserId(user.getId());
                String msg = kdfUtils.keyToString(userKey);
                userKey1.setSecretKey(msg);

                rabbitTemplate.convertAndSend(MqConstants.FUCHUANG_EXCHANGE,
                        MqConstants.FUCHUANG_INSERT_KEY, userKey1);
            } else {
                //否则，密钥存在，直接拿到密钥
                String secretKey = userKeyEntity.getSecretKey();
                userKey = kdfUtils.stringToKey(secretKey);
            }
//                        System.out.println("通过(userId" + user.getId() +  ")信息拿到的密钥:" + kdfUtils.keyToString(userKey));
            //结合密钥
            SecretKey combinedKey = kdfUtils.generateCombinedKey(key, userKey);
//                        System.out.println("整合后的密钥:" + kdfUtils.keyToString(combinedKey));
            System.out.println();


            /**
             * 获取密钥的方式都相同
             */
            String username = kdfUtils.Encoding(name, combinedKey);
            String codeMail = kdfUtils.Encoding(email, combinedKey);
            String codeTel = kdfUtils.Encoding(tel, combinedKey);
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

        return Result.success("resumeId:" + resume.getId());
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
        LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserResume::getUserId, userId)
                .eq(UserResume::getResumeId, resumeVo.getId());
        UserResume userResume = userResumeService.getOne(wrapper);
        if (userResume == null) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }

//        Resume resume = resumeMapper.selectById(resumeVo.getId());

        Resume resume = BeanCopyUtils.copyBean(resumeVo, Resume.class);

//        resume.setId(userResume.getResumeId());

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
            SecretKey combinedKey = null;
            try {
                //先拿到密钥，在进行分别回显
                UserKey userKey = KeyHttpUtils.sendGetRequest(SystemConstants.KEY_CLIENT_URL, user.getId());
                if (userKey == null) {
                    throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
                }
                //用户密钥
                SecretKeySpec urkey = kdfUtils.stringToKey(userKey.getSecretKey());
                //起始的密钥（需要固定）
                SecretKeySpec beginKey = kdfUtils.generateKey(null, null, 512);
                //结合密钥
                combinedKey = kdfUtils.generateCombinedKey(beginKey, urkey);
                if (combinedKey == null) {
                    throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
                }
            } catch (Exception e) {
                throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
            }

            //数据流操作需要保证数据固定
            SecretKey finalCombinedKey = combinedKey;
            List<ResumeVo> collect = resumeList.stream().map(item -> {
                try {

                    String decodeName = kdfUtils.Decoding(item.getName(), finalCombinedKey);
                    String email = kdfUtils.Decoding(item.getEmail(), finalCombinedKey);
                    String tel = kdfUtils.Decoding(item.getTel(), finalCombinedKey);
                    String live = kdfUtils.Decoding(item.getLive(), finalCombinedKey);

                    item.setName(decodeName)
                            .setEmail(email)
                            .setTel(tel)
                            .setLive(live);
                    ResumeVo resumeVo = BeanCopyUtils.copyBean(item, ResumeVo.class);
//                    //密钥解析
//                    Resume resumeVoByKey = getResumeInfoUtils.getResumeVoByKey(finalUser.getId(), resume);
//                    ResumeVo resumeVo = BeanCopyUtils.copyBean(resumeVoByKey, ResumeVo.class);
                    resumeVo.setIsDefault(item.getIsDefault());
                    return resumeVo;
                } catch (Exception e) {
                    throw new BaseException(SystemConstants.HAS_NO_KEY);
                }
            }).collect(Collectors.toList());

            return Result.success(collect);
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

        try {
            SecretKeySpec userKey = null;
            UserKey userKey2 = KeyHttpUtils.sendGetRequest(SystemConstants.KEY_CLIENT_URL, userId);
            //如果这个用户没有密钥，那么就先创建一个密钥
            if (userKey2 == null) {
                userKey = kdfUtils.generateKey(user.getUsername(), new byte[16], 512);
                //创建密钥
                UserKey userKey1 = new UserKey();
                userKey1.setUserId(user.getId());
                String msg = kdfUtils.keyToString(userKey);
                userKey1.setSecretKey(msg);
                //保证rabbitmq消息必须送达
                rabbitTemplate.convertSendAndReceive(MqConstants.FUCHUANG_EXCHANGE,
                        MqConstants.FUCHUANG_INSERT_KEY, userKey1);
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_HAS_NO_KEY);
        }

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

        LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserResume::getResumeId, resumeId)
                .eq(UserResume::getUserId, userId);
        UserResume userResume = userResumeService.getOne(wrapper);
        //解密Vo
        ResumeVo decodeResume = getDecodeResume(resume, userId);
        decodeResume.setIsDefault(userResume.getIsDefault());
        return Result.success(decodeResume);
    }

    /**
     * 密钥解析简历数据
     *
     * @param resume
     * @param userId
     * @return
     */
    private ResumeVo getDecodeResume(Resume resume, Integer userId) {
        try {
            //如果拿到的简历为空，说明密钥解析失败，当前用户和密钥是不匹配的
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

        List<Long> roleIds = userRoleVo.getRoleIds();
        roleIds.forEach(item -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(Long.valueOf(user.getId()))
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
    public Result<String> setResumeObscure(Integer resumeId) {

        judgeUserHasResume(resumeId);

        Resume resume = resumeService.getById(resumeId);
        if (resume == null) {
            throw new BaseException(SystemConstants.RESUME_HAS_DELETED_OR_NOT_EXIST);
        }
        Integer status = null;
        if (Objects.equals(resume.getObscure(), SystemConstants.CAN_NOT_BE_SEEN)) {
            resume.setObscure(SystemConstants.CAN_BE_SEEN);
            status = SystemConstants.CAN_BE_SEEN;
        } else {
            resume.setObscure(SystemConstants.CAN_NOT_BE_SEEN);
            status = SystemConstants.CAN_NOT_BE_SEEN;
        }
        //遍历，给每一个resume设置是否可见
        Integer finalStatus = status;
        resumeMapper.setObscureByResumeId(resumeId, status);
//                resumeIds.forEach(item -> {
//                    Resume resume1 = new Resume();
//                    resume1.setObscure(finalStatus)
//                            .setId(item.getResumeId());
//                    resumeMapper.updateById(resume1);
//                });


        return Result.success(SystemConstants.SUCCESS);
    }

    /**
     * 删除简历
     *
     * @param resumeId
     * @return
     */
    @Override
    @Transactional
    public Result<String> deleteResumeById(Integer resumeId) {
        Integer userId = judgeUserHasResume(resumeId);

        Resume resume = resumeMapper.selectById(resumeId);
        if (resume == null) {
            throw new BaseException(SystemConstants.RESUME_HAS_DELETED_OR_NOT_EXIST);
        }
        UserResume userResume = new UserResume();
        userResume.setResumeId(resumeId)
                .setUserId(userId);
        userResumeService.removeById(userResume);
        resumeService.removeById(resumeId);
        return Result.success(SystemConstants.SUCCESS);
    }


    /**
     * 判断这个用户是否拥有这个简历
     *
     * @param resumeId
     */
    private Integer judgeUserHasResume(Integer resumeId) {
        try {
            Integer userId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getUserId, userId)
                    .eq(UserResume::getResumeId, resumeId);
            wrapper.select(UserResume::getResumeId);
            UserResume userResume = userResumeService.getOne(wrapper);
            //这里判断用户是否拥有此简历
            if (userResume == null) {
                throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
            }
            return userId;
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
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

