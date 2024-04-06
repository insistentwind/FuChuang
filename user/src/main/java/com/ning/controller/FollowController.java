package com.ning.controller;

import com.ning.annotation.SecurityParameter;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.dto.FollowDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.Company;
import com.ning.domain.result.Result;
import com.ning.observer.Observer;
import com.ning.observer.Subject;
import com.ning.service.CompanyService;
import com.ning.service.FollowService;
import com.ning.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/02/24 18:10
 **/
@RestController
@Slf4j
@Api(tags = "用户关注消息列表")
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;

    /**
     * 反射创建对象
     * （根据求职者的全限定类名反射创建观察者对象）
     * @param className
     * @return
     */
    public Observer createObserver(String className) {
        try {
            Class<?> cls = Class.forName("com.recruit.common.observer.user." + className);
            Object o = cls.newInstance();
            return (Observer)o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射创建对象
     * （根据企业的全限定类名反射创建被观察者对象）
     * @param className
     * @return
     */
    public Subject createSubject(String className) {
        try {
            Class<?> cls = Class.forName("com.recruit.common.observer.company." + className);
            Object o = cls.newInstance();
            return (Subject) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增关注
     * @param followDto
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("新增关注公司")
    public Result<String> addFollow(@RequestBody FollowDto followDto){
        log.info("新增关注：{}",followDto);
        Integer companyId = followDto.getCompanyId();
        Company company = companyService.getById(companyId);
        if(company == null){
            throw new RuntimeException("没有此公司,请检查后重试");
        }
        // 调用find查询是否已经关注过此公司
        // 这是下面自己写的方法
        FollowDto one = find(followDto);
        if(one != null){
            //已经关注了
            throw new RuntimeException("已经关注此公司");
        }
       //保存此关注
        followService.insertByDto(followDto);

        //新增关注后，把观察者添加到被观察者列表中
        // 1. 根据当前登录的求职者用户名反射创建对象
            //获取当前用户
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //根据用户名反射创建对象
        Integer userId = userDto.getUser().getId();
        //这里拿到了已创建好名字的类的实例对象
//        Observer observer = createObserver(userDto.getUser().getUsername());
        // 2. 根据关注的公司id查询公司信息
//        Company company = companyService.getById(followDto.getCompanyId());
        String companyName = company.getBrandName();
//        //如果不存在公司观察对象(有文件，没对象)，则创建
//        if(!SingleUtil.map.containsKey(companyName)){
//            //3.根据关注的公司名称反射创建对象
//            Subject subject = createSubject(companyName);
//            //4.存放到singleUtil的全局Map中
//            SingleUtil.map.put(companyName,subject);
//        }
//        // 5. 添加到观察者列表中
//        // 添加到map的subject（公司）对象的list中
//        SingleUtil.map.get(companyName).addObserver(observer);

        //可以用redis进行替代
        //把<公司名字，用户名字>放入键名为companylist的hashmap中
        redisTemplate.opsForHash().put(companyName,userId.toString(),userDto.getUser().getUsername());
//        redisCache.setCacheMapValue(companyName, userDto.getUsername(), userDto.getUsername());
        return Result.success("关注成功");
    }


    /**
     * 根据用户id和公司id取消关注该公司
     * @param followDto
     * @return
     */
    @DeleteMapping("/cancel")
    @ApiOperation("取消关注")
    public Result<String> cancelFollow(@RequestBody FollowDto followDto) {
//        //先看观察者队列中有没有这个公司
//        try {
//            Company company = companyService.getById(followDto.getCompanyId());
//            //好像删不了？？
//            SingleUtil.map.get(company.getCompanyName()).removeObservers();
//        }
        try {
            Company company = companyService.getById(followDto.getCompanyId());
            redisTemplate.opsForHash().delete(company.getBrandName(),followDto.getUserId().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return followService.cancelFollow(followDto);
    }

    /**
     * 查看当前用户的所有关注列表
     * @return
     */
    @GetMapping
    @ApiOperation("查看我的关注")
    public Result<List<CompanyDto>> getAllByUserId(){
        //查看当前用户的所有关注列表
        List<CompanyDto> companyDtos = followService.getAllCompanyByUserId();
        return Result.success(companyDtos);
    }

    /**
     * 查询是否关注
     * @param followDto
     * @return
     */
    @ApiOperation("查询是否已经关注了某一个公司")
    @GetMapping("/find")
    public FollowDto find(FollowDto followDto){
        log.info("查询是否存在关注");
        return followService.getByDto(followDto);
    }
}