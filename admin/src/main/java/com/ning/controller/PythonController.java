package com.ning.controller;

import com.ning.domain.dto.ResumePageDto;
import com.ning.domain.entity.ResumeDraw;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeVo;
import com.ning.service.ResumeService;
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
 * @create: 2024/04/12 20:01
 **/
@RestController
@Slf4j
@RequestMapping("/system/python")
@Api(tags = "python专属，另一个在职位画像接口")
public class PythonController {
    @Autowired
    private ResumeService resumeService;

    /**
     * 简历池画像列表
     * @param resumePageDto
     * @return
     */
    @ApiOperation("简历池画像列表")
    @GetMapping("/resumeList")
    public Result<List<ResumeDraw>> getResumeList(ResumePageDto resumePageDto){
        return resumeService.getDrawPage(resumePageDto);
    }

    /**
     * 查看职位下简历画像
     * @param workId
     * @return
     */
    @ApiOperation("查看职位下简历画像")
    @GetMapping("/position/{id}")
    public Result<List<ResumeDraw>> getDrawByPositionId(@PathVariable(value = "id") Integer workId){
        return resumeService.getDrawByPositionId(workId);
    }


}