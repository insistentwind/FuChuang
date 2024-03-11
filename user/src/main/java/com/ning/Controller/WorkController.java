package com.ning.Controller;

import com.ning.domain.dto.NotifyDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.Notify;
import com.ning.domain.entity.Relation;
import com.ning.domain.entity.Work;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.systemConstants.SystemConstants;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.service.CompanyService;
import com.ning.service.NotifyService;
import com.ning.service.RelationService;
import com.ning.service.WorkService;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 根据分类id查询信息
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    @ApiOperation("根据分类id查询信息，由于没有分类信息，暂定")
    public Result<List<WorkVo>> getList(@PathVariable Long id){
        return workService.getList(id);
    }

    private void check(){
        Integer isCompany;
        try {
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isCompany = userDto.getUser().getIsCompany();
        }
        catch (Exception e){
            throw new BaseException("请先登录后再操作");
        }
        if(!isCompany.equals(SystemConstants.IS_COMPANY)){
            throw new BaseException("没有该权限");
        }
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
        check();

        Integer companyId = work.getCompanyId();
        Company company = companyService.getById(companyId);
        String companyName = company.getCompanyName();


        // 判断全局map中是否存在对应的被观察者类
//        if (!redisTemplate.hasKey(companyName)) {
//            // 不存在此键
//            throw new RuntimeException("不存在此公司");
//        }

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
        work.setCompany(companyName);
        workService.save(work);
        Relation relation = new Relation();
        relation.setWorkId(work.getId()).setCompanyId(companyId);
        relationService.save(relation);
        return Result.success();
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
        check();
        //todo 公司名称不能更改，这里要自动更新
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
        check();
        for (Integer id : ids) {
            Work work = workService.getById(id);
//            LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Company::getCompanyName,work.getCompany());
//            Company company = companyService.getOne(wrapper);
            String companyName = work.getCompany();
            try {
                Relation relation = new Relation();
                relation.setCompanyId(work.getCompanyId())
                        .setWorkId(work.getId());

                relationService.removeById(relation);
            }
            catch (Exception e){

            }


            String message = companyName + "下架了职位——" + work.getTitle() + "，快去看看吧！";

//            if (redisTemplate.hasKey(companyName) == null) {
//                throw new RuntimeException("不存在此公司");
//            }
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

            }

        }
        return workService.deleteByIds(ids);
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
}
