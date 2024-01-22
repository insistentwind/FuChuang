package com.ning.Controller;

import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.Work;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;
import com.ning.service.WorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2024/1/9 23:26
 */
@RestController
@Slf4j
@Api(tags = "简历内容相关接口")
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

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
     * 新增职位接口
     * @param work
     * @return
     */
    @ApiOperation("新增职位")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Work work){
        log.info("需要新增的职位信息：{}",work);
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
        return workService.deleteByIds(ids);
    }
}
