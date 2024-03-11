package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.MessageConstant;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.ResumeVo;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.User;
import com.ning.domain.entity.UserRole;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserPageVo;
import com.ning.domain.vo.UserRoleVo;
import com.ning.domain.vo.UserVo;
import com.ning.exception.BaseException;
import com.ning.mapper.UserMapper;
import com.ning.mapper.UserRoleMapper;
import com.ning.service.ResumeService;
import com.ning.service.UserRoleService;
import com.ning.service.UserService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.JwtUtil;
import com.ning.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
   private ResumeService resumeService;
   @Autowired
   private UserRoleService userRoleService;

    /**
     * 用户登录接口
     * @param user
     * @return
     */
    @Override
    public Result<UserVo> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        //security会自动调用这里面的userdetailsservice方法进行登录校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //TODO 没有进行到这一步
//        System.err.println(authenticate);
        //TODO 看是账号不存在还是密码错误
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new BaseException(MessageConstant.PLEASE_CHECK);
        }
        //获取用户id生成jwt存入到token
        UserDto userDto = (UserDto) authenticate.getPrincipal();
        int userId = userDto.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(userId));
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.USER_LOGIN+userId,userDto);
        //把token和userinfo封装 返回

        user = userDto.getUser();
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        userVo.setJwt(jwt);
        return Result.success(userVo);
    }
    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public Result<String> register(User user) {
        //验证邮箱等个人信息
        if(!StringUtils.hasText(user.getUsername()) ||
                !StringUtils.hasText(user.getPassword()) ||
                !StringUtils.hasText(user.getMail())
//                !StringUtils.hasText(user.getName()) ||
//                !StringUtils.hasText(user.getIdCard()) ||
//                !StringUtils.hasText(user.getTele())
        ){
            //用户名，昵称，邮箱，密码
            throw new BaseException(MessageConstant.PERSONAL_MSG_NOT_COMPLETE);
        }
        if(usernameExist(user.getUsername())){
            //账号已存在
            throw new BaseException(MessageConstant.ACCOUNT_ALREADY_EXISTS);
        }
//        else if(nameExist(user.getName())){
//            //用户名已存在
//            throw new Exception(MessageConstant.NAME_ALREADY_EXISTS);
//        }
        if (!StringUtils.hasText(user.getName())){
            String name = "fuchuang" + UUID.randomUUID().toString();
            user.setName(name);
        }
        //密码加密
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setCreateTime(LocalDateTime.now());
        save(user);
        // 用户注册成功，创建一个该用户对应的观察者类
//        ObserverGenerate.generate(user.getUsername());
        return Result.success("注册成功");
    }

    /**
     * 获取当前用户的简历信息
     * @return
     */
    @Override
    public Result<ResumeVo> getReusme() {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDto.getUser();
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resume::getUserId,user.getId());
        Resume resume = resumeService.getOne(wrapper);
        ResumeVo resumeVo = BeanCopyUtils.copyBean(resume, ResumeVo.class);
        return Result.success(resumeVo);
    }

    /**
     * 修改当前用户的信息
     * @param user
     * @return
     */
    @Override
    public Result<String> updateByUser(User user) {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User nowUser = userDto.getUser();
        Integer id = nowUser.getId();
        if(id == null){
            throw new BaseException("当前用户信息为空,请重试");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,id);
        if (StringUtils.hasText(user.getPassword())){
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        update(user,wrapper);
        return Result.success("修改成功");
    }

    /**
     * 用户注销
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
        wrapper.like(StringUtils.hasText(userPageVo.getTele()),User::getTele,userPageVo.getTele())
                .like(StringUtils.hasText(userPageVo.getUsername()),User::getUsername,userPageVo.getUsername())
                .eq(StringUtils.hasText(userPageVo.getStatus()),User::getStatus, userPageVo.getStatus());
        Page<User> page = new Page<>(userPageVo.getPageNum(), userPageVo.getPageSize());
        page(page,wrapper);
        List<User> records = page.getRecords();
        return Result.success(new PageResult(records.size(),records));
    }

    /**
     * 新增用户
     * @param userRoleVo
     * @return
     */
    @Override
    public Result<String> insertByUserRoleVo(UserRoleVo userRoleVo) {

        //​	需要新增用户功能。新增用户时可以直接关联角色。
        //​	注意：新增用户时注意密码加密存储。
        String password = passwordEncoder.encode(userRoleVo.getPassword());
        //​	用户名不能为空，否则提示：必需填写用户名
        if(userRoleVo.getUserName().isEmpty()){
            throw new RuntimeException("错误，必须填写用户名");
        }
        //​	用户名必须之前未存在，否则提示：用户名已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,userRoleVo.getUserName());
        User one = getOne(wrapper);
        if(one != null){
            throw new RuntimeException("新增失败，用户名已存在");
        }
        //​	邮箱必须之前未存在，否则提示：邮箱已存在
        LambdaQueryWrapper<User> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(User::getMail,userRoleVo.getMail());
        User third = getOne(wrapper3);
        if(third != null){
            throw new RuntimeException("新增失败，邮箱已存在");
        }
        User user = BeanCopyUtils.copyBean(userRoleVo, User.class);
        user.setPassword(password);
        save(user);
        Integer userId = user.getId();
        List<String> roleIds = userRoleVo.getRoleIds();
        roleIds.forEach(item -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(userId).setRoleId(Integer.valueOf(item));
            userRoleService.save(userRole);
        });
        return Result.success();
    }
    /**
     * 删除固定的某个用户（逻辑删除）
     * @param ids
     * @return
     */
    @Override
    public Result<String> deleteById(List<Long> ids) {
        ids.forEach(this::removeById);
        return Result.success("删除成功");
    }

    private boolean usernameExist(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        return count(wrapper) > 0;
    }
    private boolean nameExist(String name){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,name);
        return count(wrapper) > 0;
    }

}

