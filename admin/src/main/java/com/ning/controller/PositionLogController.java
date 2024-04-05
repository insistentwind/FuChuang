package com.ning.controller;

import com.ning.domain.entity.WorkLog;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkLogVo;
import com.ning.service.WorkLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/04/05 16:03
 **/
@RestController
@Api(tags = "职位操作失败日志")
@RequestMapping("/log/control")
@Slf4j
public class PositionLogController {

    @Autowired
    private WorkLogService WorkLogService;

    /**
     * 获取操作失败的职位list
     * @param workLogVo
     * @return
     */
    @ApiOperation("获取操作失败的职位list")
    @PostMapping("/list")
    public Result<List<WorkLog>> getWorkLogByFlag(@RequestBody (required = false) WorkLogVo workLogVo){
        return WorkLogService.getListByVo(workLogVo);
    }
}