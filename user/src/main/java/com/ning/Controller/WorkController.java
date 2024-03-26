package com.ning.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.dto.ResumeCommitDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.*;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.constants.SystemConstants;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.service.*;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Author: qjn
 * @Date: 2024/1/9 23:26
 */
@RestController
@Slf4j
@Api(tags = "职位相关接口")
@RequestMapping("/work")
public class WorkController {

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

    /**
     * 分页条件查询对应职位
     *
     * @param workDto
     * @return
     */
    @ApiOperation("分页条件查询对应职位")
    @GetMapping("/page")
    public Result<PageResult> page(WorkDto workDto) {
        log.info("分页条件查询对应职位:{}", workDto);
        return workService.getListByTag(workDto);
    }


    private User check(){
        Integer isCompany;
        UserDto userDto;
        try {
            userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isCompany = userDto.getUser().getIsCompany();
        }
        catch (Exception e){
            throw new BaseException("请先登录后再操作");
        }
        if(!isCompany.equals(SystemConstants.IS_COMPANY)){
            throw new BaseException("没有该权限");
        }
        return userDto.getUser();
    }

    /**
     * 发布职位接口
     *
     * @param work
     * @return
     */
    @ApiOperation("发布职位,需要权限，但暂未设置权限")
    @PostMapping("/save")
    @Transactional
    public Result<String> save(@RequestBody Work work) {
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
            wrapper.eq(UserCompany::getUserId,userDto.getUser().getId());
            UserCompany userCompany = userCompanyService.getOne(wrapper);
            Company company = companyService.getById(userCompany.getCompanyId());

            companyName = company.getBrandName();
            companyId = company.getId();
        } catch (Exception e){
            throw new BaseException("当前用户未绑定公司");
        }


//        Integer companyId = work.getCompanyId();
//        Company company = companyService.getById(companyId);
//        String companyName = company.getCompanyName();



        String message = companyName + "发布了新职位——" + work.getTitle() + ",快去看看吧";
        //TODO 还需要做的是启动的时候把所有的观察者和被观察者放入redis中
        try {
            Set<String> set = redisTemplate.opsForHash().keys(companyName);
            //这里之前已经在对应的观察者子类中，存入SingleUtil.messageMap.put(name, message);对应的消息
            for (String key : set) {
                NotifyDto notifyDto = new NotifyDto();
                notifyDto.setUserId(Integer.valueOf(key));
                // 此处是发布了新的职位
//            notifyDto.setContent(SingleUtil.messageMap.get(key));//通知的信息，需要更改
                notifyDto.setContent(message);
                Notify notify = BeanCopyUtils.copyBean(notifyDto, Notify.class);
                notify.setTime(LocalDateTime.now());
                //保存消息
                notifyService.save(notify);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        workService.save(work);
        Relation relation = new Relation();
        relation.setWorkId(work.getId()).setCompanyId(companyId);
        relationService.save(relation);
        return Result.success("职位新增成功");
    }

    /**
     * 根据id查询职位详细信息,回显
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询职位详细信息")
    @GetMapping("/{id}")
    public Result<WorkVo> getById(@PathVariable Integer id) {
        log.info("查询的职位id：{}", id);
        return workService.getByWorkId(id);
    }

    /**
     * 更新职位信息
     *
     * @param workDto
     * @return
     */
    @ApiOperation("更新职位信息")
    @PutMapping
    public Result<String> update(@RequestBody WorkDto workDto) {
        log.info("更新职位信息:{}", workDto);
        User user = check();

        if(!userCompanyService.judgePriByUserId(user.getId(),workDto.getId())){
            //判断当前用户是否是该公司的职位发布者
            throw new BaseException("error,可能的错误是您没有权限修改其他公司的职位信息");
        }
        return workService.updateByWork(workDto);
    }

    /**
     * 批量删除职位
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除职位")
    @DeleteMapping
    @Transactional
    public Result<String> delete(@RequestParam List<Integer> ids) {
        log.info("需要删除的职位信息:{}", ids);

        User user1 = check();
        for (Integer id : ids) {

            if(!userCompanyService.judgePriByUserId(user1.getId(),id)){
                //判断当前用户是否是该公司的职位发布者
                throw new BaseException("error,可能的错误是您没有权限修改其他公司的职位信息");
            }

            Work work = workService.getById(id);
//            LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Company::getCompanyName,work.getCompany());
//            Company company = companyService.getOne(wrapper);
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId,user1.getId());
            UserCompany userCompany = userCompanyService.getOne(wrapper);

            LambdaQueryWrapper<Company> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Company::getId,userCompany.getCompanyId());
            Company company = companyService.getOne(wrapper1);

            String companyName = company.getBrandName();
            //这里是删除对应的公司-职位关系表
            try {
                Relation relation = new Relation();
                relation.setCompanyId(work.getCompanyId())
                        .setWorkId(work.getId());

                workService.removeById(work.getId());
                relationService.removeById(relation);
            }
            catch (Exception e){
                e.printStackTrace();
            }


            String message = companyName + "下架了职位——" + work.getTitle() + "，快去看看吧！";

//            if (redisTemplate.hasKey(companyName) == null) {
//                throw new RuntimeException("不存在此公司");
//            }
            //这里是删除redis中存储的公司键
            try {
                Set<String> set = redisTemplate.opsForHash().keys(companyName);
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String user = iterator.next();
                    NotifyDto notifyDto = new NotifyDto();
                    notifyDto.setUserId(Integer.valueOf(user));
                    notifyDto.setContent(message);
                    Notify notify = BeanCopyUtils.copyBean(notifyDto, Notify.class);
                    notify.setTime(LocalDateTime.now());
                    notifyService.save(notify);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return workService.deleteByIds(ids);
    }

    /**
     * 用户投递简历接口
     * @param resumeCommitDto
     * @return
     */
    //用户投递简历就是添加到历史记录中去
    @GetMapping("/commitResume")
    @ApiOperation("用户投递简历接口或者是允许对面查看自己的简历")
    public Result<String> commitResume(ResumeCommitDto resumeCommitDto){
        return workService.commitResume(resumeCommitDto);
    }

    /**
     * 更新redis中对应的职位浏览量
     *
     * @param id
     * @return
     */
    @PutMapping("/updateViewCount/{id}")
    @ApiOperation("更新职位浏览量")
    public Result<String> updateViewCount(@PathVariable("id") Long id) {
        log.info("需要更新浏览的职位id是：{}", id);
        return workService.updateViewCount(id);
    }

    /**
     * todo 考虑赛事方（一个用户）如何批量向投入多份简历到职位中
     *
     * TODO 缺少新增职位时，要同步更新redis中的数据
     */


}
