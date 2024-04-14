package com.ning.controller;

import com.ning.domain.dto.ResumePageDto;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeVo;
import com.ning.service.CompanyService;
import com.ning.service.ResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/04/09 20:52
 **/
@Slf4j
@RestController
@RequestMapping("/resumePool")
@Api(tags = "公共简历池")
public class ResumePoolController {
    @Autowired
    private ResumeService resumeService;

    /**
     * 查询公共简历池数据
     * @param resumePageDto
     * @return
     */
    @ApiOperation("查询公共简历池数据")
    @GetMapping("/list")
    public Result<List<ResumeVo>> getResumeList(ResumePageDto resumePageDto){
        return resumeService.getPage(resumePageDto);
    }


    /**
     * 根据id查询简历数据
     * @param resumeId
     * @return
     */
    @ApiOperation("根据id查询简历数据")
    @GetMapping("/{id}")
    public Result<ResumeVo> getResumeVoByResumeId(@PathVariable(value = "id") Integer resumeId){
        return resumeService.getResumeVoByResumeId(resumeId);
    }
}