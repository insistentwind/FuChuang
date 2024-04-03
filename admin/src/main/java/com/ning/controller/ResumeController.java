package com.ning.controller;

import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeVo;
import com.ning.domain.vo.WorkVo;
import com.ning.service.CompanyService;
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
 * @create: 2024/03/30 22:28
 **/
@RestController
@Slf4j
@RequestMapping("/system/resume")
@Api(tags = "招聘简历管理")
public class ResumeController {
    @Autowired
    private CompanyService companyService;
    /**
     * 公司端投递简历
     * @param resumeVoList
     * @return
     */
    @ApiOperation("公司端投递简历(公司把面试者的信息录入)")
    @GetMapping("/resume")
    public Result<String> commitResumeList(List<ResumeVo> resumeVoList){
        return companyService.commitResumeList(resumeVoList);
    }

    /**
     * 查询此公司下所有职位投递的简历列表
     * @param
     * @return
     */
    @ApiOperation("此公司所有职位收到的简历列表")
    @GetMapping("/listByCompany")
    public Result<List<WorkVo>> getResumeListByCompany(){
        return companyService.getResumeListByCompany();
    }


    /**
     * 根据职位id查询所有投递的简历列表
     * @param id
     * @return
     */
    @ApiOperation("职位id查询投递的简历列表")
    @GetMapping("/listByResumeId/{id}")
    public Result<WorkVo> getResumeListByWorkId(@PathVariable Integer id){
        //这里的简历列表被放入到了WorkVo中的ResumeList中
        return companyService.getResumeListByWorkId(id);
    }


    /**
     * 据id查询简历
     * @return
     */
    @ApiOperation("据id查询简历")
    @GetMapping("/getByResumeId")
    public Result<ResumeVo> getResumeVoByResumeId(Integer ResumeId){
        return companyService.getResumeVoByResumeId(ResumeId);
    }



}