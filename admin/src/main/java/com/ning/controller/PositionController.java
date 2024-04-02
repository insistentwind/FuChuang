package com.ning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.constants.SystemConstants;
import com.ning.domain.Do.WorkDo;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.*;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeVo;
import com.ning.domain.vo.WorkPageVo;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.service.*;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author: qjn
 * @create: 2024/03/30 22:29
 **/
@Api(tags = "公司职位接口")
@Slf4j
@RestController
@RequestMapping("/system/position")
public class PositionController {


    @Autowired
    private WorkService workService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RelationService relationService;
    @Autowired
    private UserCompanyService userCompanyService;


    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 发布职位接口
     *
     * @param workDo
     * @return
     */
    // todo 权限设置了么？
    @ApiOperation("发布职位")
    @PostMapping("/save")
    public Result<String> save(@RequestBody WorkDo workDo) {
        Work work = BeanCopyUtils.copyBean(workDo,Work.class);
        log.info("需要新增的职位信息：{}", work);
        //职位发布后，通知对应的观察者（用户）
//        String companyName = work.getCompany();
        //检查当前用户是否是公司
        check();

        String companyName;
        Integer companyId;

        try {
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId, userDto.getUser().getId());
            UserCompany userCompany = userCompanyService.getOne(wrapper);
            Company company = companyService.getById(userCompany.getCompanyId());

            companyName = company.getBrandName();
            companyId = company.getId();
        } catch (Exception e) {
            throw new BaseException("当前用户未绑定公司");
        }


        String message = companyName + "发布了新职位——" + work.getTitle() + ",快去看看吧";
        //还需要做的是启动的时候把所有的观察者和被观察者放入redis中
//            Set<String> set = redisTemplate.opsForHash().keys(companyName);
//            //这里之前已经在对应的观察者子类中，存入SingleUtil.messageMap.put(name, message);对应的消息
        sendMessage(companyName,message);

        transactionTemplate.execute((status) ->{
            workService.save(work);
            Relation relation = new Relation();
            relation.setWorkId(work.getId()).setCompanyId(companyId);
            relationService.save(relation);
            return null;
        });

        redisTemplate.opsForHash().put(SystemConstants.WORK_VIEW_COUNT, work.getId().toString(), 0);

        return Result.success("职位新增成功");
    }



    /**
     * 更新职位信息
     *
     * @param workDo
     * @return
     */
    @ApiOperation("更新职位信息")
    @PutMapping
    public Result<String> update(@RequestBody WorkDo workDo) {
        WorkVo workVo = BeanCopyUtils.copyBean(workDo, WorkVo.class);
        log.info("更新职位信息:{}", workVo);
        User user = check();

        if (!userCompanyService.judgePriByUserId(user.getId(), workVo.getId())) {
            //判断当前用户是否是该公司的职位发布者
            throw new BaseException("error,可能的错误是您没有权限修改其他公司的职位信息");
        }
        return workService.updateByWork(workVo);
    }



    /**
     * 批量删除职位
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除职位")
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Integer> ids) {
        log.info("需要删除的职位信息:{}", ids);

        User user1 = check();
        for (Integer id : ids) {

            if (!userCompanyService.judgePriByUserId(user1.getId(), id)) {
                //判断当前用户是否是该公司的职位发布者
                throw new BaseException("error,可能的错误是您没有权限修改其他公司的职位信息");
            }

            Work work = workService.getById(id);
//            LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Company::getCompanyName,work.getCompany());
//            Company company = companyService.getOne(wrapper);
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId, user1.getId());
            UserCompany userCompany = userCompanyService.getOne(wrapper);

            LambdaQueryWrapper<Company> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Company::getId, userCompany.getCompanyId());
            Company company = companyService.getOne(wrapper1);

            String companyName = company.getBrandName();
            //这里是删除对应的公司-职位关系表


            String message = companyName + "下架了职位——" + work.getTitle() + "，快去看看吧！";

//            if (redisTemplate.hasKey(companyName) == null) {
//                throw new RuntimeException("不存在此公司");
//            }
            //这里是删除redis中存储的公司键
            // 如果某一个用户不再关注这个公司了，那么也要删除redis中保存的键
            sendMessage(companyName, message);

            try {
                Relation relation = new Relation();
                relation.setCompanyId(work.getCompanyId())
                        .setWorkId(work.getId());

                transactionTemplate.execute((status) -> {
                    workService.removeById(work.getId());
                    relationService.removeById(relation);
                    return null;
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return Result.success(SystemConstants.SUCCESS);
    }

    /**
     * 联动职位发布信息
     *
     * @param companyName
     * @param message
     */
    @Async("commonAsyncThreadPool")
    public void sendMessage(String companyName, String message) {
        try {

            Set<String> set = redisTemplate.opsForHash().keys(companyName);
            for (String user : set) {
                NotifyDto notifyDto = new NotifyDto();
                notifyDto.setUserId(Integer.valueOf(user));
                notifyDto.setContent(message);
                Notify notify = BeanCopyUtils.copyBean(notifyDto, Notify.class);
                notify.setTime(LocalDateTime.now());
                notifyService.save(notify);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询职位详细信息,回显
     *
     * @param id
     * @return
     */
    @ApiOperation("id查询职位详细信息")
    @GetMapping("/{id}")
    public Result<WorkVo> getById(@PathVariable Integer id) {
        log.info("查询的职位id：{}", id);
        return workService.getByWorkId(id);
    }



    /**
     * 条件查询此公司下发布的职位
     * @param workPageVo
     * @return
     */
    @ApiOperation("条件查询此公司下的职位")
    @GetMapping("/PositionList")
    public Result<List<WorkVo>> pageByCategoryId(WorkPageVo workPageVo){
        return companyService.pageByCategoryId(workPageVo);
    }

    /**
     * 根据用户id查询此用户的简历
     * @return
     */
    @ApiOperation("据用户id查询此用户的简历")
    @GetMapping("/getByUserId")
    public Result<ResumeVo> getResumeVoByUserId(Integer userId){
        return companyService.getResumeVoByUserId(userId);
    }

    private User check() {
        Integer isCompany;
        UserDto userDto;
        try {
            userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isCompany = userDto.getUser().getIsCompany();
        } catch (Exception e) {
            throw new BaseException("请先登录后再操作");
        }
        if (!isCompany.equals(SystemConstants.IS_COMPANY)) {
            throw new BaseException("没有该权限");
        }
        return userDto.getUser();
    }

}