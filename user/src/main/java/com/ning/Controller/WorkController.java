package com.ning.Controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.Notify;
import com.ning.domain.entity.Work;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;
import com.ning.mapper.CompanyMapper;
import com.ning.service.CompanyService;
import com.ning.service.NotifyService;
import com.ning.service.WorkService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SingleUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 分页条件查询对应简历内容
     * @param workDto
     * @return
     */
    @ApiOperation("分页条件查询对应职位内容")
    @GetMapping ("/page")
    public Result<PageResult> page(WorkDto workDto){
        log.info("分页条件查询对应简历内容:{}",workDto);
        return workService.getListByTag(workDto);
    }

    /**
     * 发布职位接口
     * @param work
     * @return
     */
    @ApiOperation("发布职位")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Work work){
        log.info("需要新增的职位信息：{}",work);
        //职位发布后，通知对应的观察者（用户）
        String companyName = work.getCompany();
        // 判断全局map中是否存在对应的被观察者类
        if(SingleUtil.map.containsKey(companyName)){
            //2.通知观察者发布职位
            SingleUtil.map.get(companyName).setState(1);
            //职位名称
            SingleUtil.map.get(companyName).setPositionName(work.getTitle());
            //调用方法，让公司类通知其所有观察者类
            SingleUtil.map.get(companyName).notifyObservers();
            // 3. 将工具类SingleUtil中的全局messageMap中的数据一一存放到通知表notify中
            Set<String> set = SingleUtil.messageMap.keySet();
            //这里之前已经在对应的观察者子类中，存入SingleUtil.messageMap.put(name, message);对应的消息
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                NotifyDto notifyDto = new NotifyDto();
                notifyDto.setUsername(key);
                notifyDto.setContent(SingleUtil.messageMap.get(key));
                Notify notify = BeanCopyUtils.copyBean(notifyDto, Notify.class);
                //保存消息
                notifyService.save(notify);
            }
            //4.清空messageMap中的数据
            SingleUtil.messageMap.clear();
        }
        return workService.saveByWork(work);
    }

    /**
     * 根据id查询职位,回显
     * @param id
     * @return
     */
    @ApiOperation("根据id查询职位")
    @GetMapping("/{id}")
    public Result<WorkVo> getById(@PathVariable Integer id){
        log.info("查询的职位id：{}",id);
        return workService.getByWorkId(id);
    }

    /**
     * 更新职位信息
     * @param workDto
     * @return
     */
    @ApiOperation("更新职位信息")
    @PutMapping
    public Result<String> update(@RequestBody WorkDto workDto){
        log.info("更新职位信息:{}",workDto);
        //todo 公司名称是否可以更改
        return workService.updateByWork(workDto);
    }

    /**
     * 批量删除职位
     * @param ids
     * @return
     */
    @ApiOperation("批量删除职位")
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Integer> ids){
        log.info("需要删除的职位信息:{}",ids);

        for (Integer id : ids) {
            Work work = workService.getById(id);
//            LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Company::getCompanyName,work.getCompany());
//            Company company = companyService.getOne(wrapper);
            String companyName = work.getCompany();
            // 判断全局map中是否存在对应的被观察者类
            if (SingleUtil.map.containsKey(companyName)) {
                // 2. 通知所有观察者下架职位了
                SingleUtil.map.get(companyName).setState(0);
                SingleUtil.map.get(companyName).setPositionName(work.getTitle());
                SingleUtil.map.get(companyName).notifyObservers();
                // 3. 将工具类SingleUtil中的全局messageMap中的数据一一存放到通知表notify中
                Set<String> set = SingleUtil.messageMap.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    NotifyDto notifyDtO = new NotifyDto();
                    notifyDtO.setUsername(key);
                    notifyDtO.setContent(SingleUtil.messageMap.get(key));
                    Notify notify = BeanCopyUtils.copyBean(notifyDtO, Notify.class);
                    notifyService.save(notify);
                }
                // 4. 清空全局messageMap中的数据
                SingleUtil.messageMap.clear();
            }

        }

        return workService.deleteByIds(ids);


    }

    /**
     * 更新redis中对应的职位浏览量
     * @param id
     * @return
     */
    @PutMapping("/updateViewCount/{id}")
    @ApiOperation("更新职位浏览量")
    public Result<String> updateViewCount(@PathVariable("id") Long id){
        log.info("需要更新浏览的职位id是：{}",id);
        return workService.updateViewCount(id);
    }
}
