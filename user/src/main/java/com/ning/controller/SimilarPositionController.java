package com.ning.controller;

import com.ning.domain.entity.SimilarPosition;
import com.ning.domain.entity.Work;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;
import com.ning.mapper.SimilarPositionMapper;
import com.ning.service.SimilarPositionService;
import com.ning.service.impl.SimilarPositionServiceImpl;
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
 * @author: qjn
 * @create: 2024/04/12 19:11
 **/
@RestController
@RequestMapping("/similar")
@Slf4j
@Api(tags = "相似职位")
public class SimilarPositionController {
    @Autowired
    private SimilarPositionService similarPositionService;

    /**
     * 传入职位id查找相似职位
     * @param workId
     * @return
     */
    @ApiOperation("传入职位id查找相似职位")
    @GetMapping("/{id}")
    public Result<List<WorkVo>> getSimilarByWorkId(@PathVariable(value = "id")Integer workId){
        return similarPositionService.getSimilarByWorkId(workId);
    }
}