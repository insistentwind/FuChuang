package com.ning.Controller;

import com.ning.domain.dto.ResumeVo;
import com.ning.domain.result.Result;
import com.ning.domain.vo.HistoryVo;
import com.ning.domain.vo.WorkVo;
import com.ning.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2024/1/20 21:55
 */
@Slf4j
@Api(tags = "查询用户浏览职位的历史记录")
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    /**
     * 查询当前用户的浏览历史
     * @return
     */
    @ApiOperation("当前用户浏览职位历史记录")
    @GetMapping
    public Result<List<HistoryVo>> getHistoryByUserId(){
        log.info("查询当前用户历史记录");
        return historyService.getHistoryByUser();
    }

    /**
     * 根据历史记录id查询职位详细信息
     * @param id
     * @return
     */
    @ApiOperation("据记录id查询职位详细")
    @GetMapping("/{id}")
    public Result<WorkVo> getHistoryById(@PathVariable Integer id){
        return historyService.getHistoryById(id);
    }
}
