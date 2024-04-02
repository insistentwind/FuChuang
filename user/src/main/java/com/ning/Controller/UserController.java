package com.ning.Controller;

import com.ning.domain.vo.ResumeVo;
import com.ning.domain.dto.UserLoginDto;
import com.ning.domain.dto.UserRegisterDto;
import com.ning.domain.entity.User;
import com.ning.domain.result.Result;
import com.ning.domain.vo.DeliverVo;
import com.ning.domain.vo.UserVo;
import com.ning.enums.AppHttpCodeEnum;
import com.ning.exception.SystemException;
import com.ning.service.UserService;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2024/1/16 12:08
 */
@RestController
@Slf4j
@Api(tags = "用户相关接口")
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;


    // TODO 用户需要设置简历的隐私，加密
    /**
     * 登录接口
     * @param userLoginDto
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录接口")
    public Result<UserVo> login(@RequestBody UserLoginDto userLoginDto){
        log.info("用户登录:{}",userLoginDto);
        User user = BeanCopyUtils.copyBean(userLoginDto, User.class);
        if(!StringUtils.hasText(user.getUsername()) ||
                !StringUtils.hasText(user.getPassword())){
            //提示需要传输用户名或者密码
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME_OR_PASSWORD);
        }
        return userService.login(user);
    }

    /**
     * 用户注册
     * @param userRegisterDto
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<String> userRegister(@RequestBody UserRegisterDto userRegisterDto){
        log.info("用户注册的信息：{}",userRegisterDto);
        User user = BeanCopyUtils.copyBean(userRegisterDto, User.class);
        return userService.register(user);
    }

    /**
     * 根据id获取用户的简历
     * @param resumeId
     * @return
     */
    @ApiOperation("根据id获取用户的简历")
    @GetMapping("/resumeById")
    public Result<ResumeVo> getResumeById(Integer resumeId){
        log.info("根据id获取用户的简历:{}",resumeId);
        return userService.getResumeById(resumeId);
    }
    /**
     * 获取当前用户默认的简历信息
     * @return
     */
    @ApiOperation("获取当前用户默认的简历信息")
    @GetMapping("/resume")
    public Result<ResumeVo> getUserResume(){
        log.info("获取当前用户的简历信息");
        return userService.getReusme();
    }


    /**
     * 简历数据修改
     * @param resumeVo
     * @return
     */
    @ApiOperation("简历数据修改")
    @PostMapping("/modify")
    public Result<String> resumeModify(@RequestBody ResumeVo resumeVo){
        return userService.resumeModify(resumeVo);
    }

    /**
     * 用户创建简历
     * @param resumeVo
     * @return
     */
    @ApiOperation("创建简历")
    @PostMapping("/insert")
    public Result<String> insertResume(@RequestBody ResumeVo resumeVo){
        log.info("用户创建简历");
        return userService.insertResume(resumeVo);
    }
    /**
     * 修改当前用户的信息
     * @param user
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改当前用户的信息")
    public Result<String> update(@RequestBody UserVo user){
        log.info("修改的用户信息是：{}",user);
        return userService.updateByUser(user);
    }

    /**
     * 用户信息回显
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("用户信息回显")
    public Result<UserVo> getUserInfo(){
        return userService.getInfo();
    }

    /**
     * 查询当前用户的投递记录
     * @return
     */
    @GetMapping("/get")
    @ApiOperation("查询当前用户的投递记录")
    public Result<List<DeliverVo>> getDeliverHistory(){
        return userService.getDliverHistory();
    }

    /**
     * 用户注销
     * @return
     */
    @ApiOperation("用户注销")
    @PostMapping("/logout")
    public Result<String> logout(){
        return userService.logout();
    }

    /**
     * 设置为默认简历
     * @param resumeId
     * @return
     */
    @PutMapping("/default")
    @ApiOperation("设置为默认简历")
    public Result<String> setDefaultResume(Integer resumeId){
        return userService.setDefaultResume(resumeId);
    }


    /**
     * 当前用户所创建的简历列表
     * @return
     */
    @GetMapping("/resumeList")
    @ApiOperation("当前用户创建的简历列表")
    public Result<List<ResumeVo>> getResumeList(){
        return userService.getResumeList();
    }

    /**
     * 获取当前用户的简历 --》获取当前用户的默认简历
     * 增加：1.设置为默认简历,2.查询当前用户的简历列表
     * todo 简历投递表要设置用户投递的简历id
     * 还有一个自己简历列表的回显
     * 公司查询某个人的简历时要显示list
     */

    /**
     * 批量创建简历
     * @param resumeVos
     * @return
     */
    @PostMapping("/cmtBatch")
    @ApiOperation("批量创建简历(maybe used)")
    public Result<String> deliverBatchResumes(@RequestBody List<ResumeVo> resumeVos){
        return userService.deliverBatchResumes(resumeVos);
    }




}
