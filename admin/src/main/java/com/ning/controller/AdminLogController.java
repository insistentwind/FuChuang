package com.ning.controller;

import com.ning.domain.entity.WorkLog;
import com.ning.domain.result.Result;
import com.ning.domain.vo.AdminWorkLogVo;
import com.ning.domain.vo.WorkLogVo;
import com.ning.domain.vo.WorkVo;
import com.ning.service.WorkLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/04/11 22:20
 **/
@RestController
@Api(tags = "(前端)职位操作日志")
@RequestMapping("/log/front")
@Slf4j
public class AdminLogController {
    @Autowired
    private WorkLogService WorkLogService;

    /**
     * 条件查询职位日志list
     * @param workLogVo
     * @return
     */
    @ApiOperation("条件查询职位日志list")
    @PostMapping("/list")
    public Result<List<AdminWorkLogVo>> getWorkLogByFlag(@RequestBody(required = false) WorkLogVo workLogVo){
        return WorkLogService.getWorkVoListByVo(workLogVo);
    }

    /**
     * 根据id获取职位信息
     * @param workId
     * @return
     */
    @ApiOperation("根据id获取职位信息")
    @GetMapping("/list/{workId}")
    public Result<List<AdminWorkLogVo>> getWorkLogByWorkId(@PathVariable Integer workId){
        return WorkLogService.getWorkVoListByWorkId(workId);
    }

    /**
     * 批量删除日志信息
     * @param ids
     * @return
     */
    @ApiOperation("批量删除日志信息")
    @DeleteMapping("/{ids}")
    public Result<String> deleteBatch(@PathVariable List<Integer> ids){
        return WorkLogService.deleteBatch(ids);
    }

    /**
     * 根据vo修改日志信息
     * @param workLogVo
     * @return
     */
    @ApiOperation("根据vo修改日志信息")
    @PutMapping("/update")
    public Result<String> updateByVo(@RequestBody WorkLogVo workLogVo){
        return WorkLogService.updateByVo(workLogVo);
    }
}