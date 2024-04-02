package com.ning.Controller;

import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.service.ClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/21 22:43
 **/
@RestController
@Slf4j
@RequestMapping("/classify")
@Api(tags = "各种分类相关接口")
public class CategoryController {
    @Autowired
    private ClassifyService classifyService;


    /**
     * 职位分类
     * @return
     */
    //workclassify
    @ApiOperation("所有职位分类")
    @GetMapping("/workCategoryList")
    public Result<List<ClassifyShowListVo>> listAllCategory(){
        return classifyService.getNormalCategoryList();
    }

    /**
     * id查询职位分类
     * @param id
     * @return
     */
    @ApiOperation("id查询职位分类")
    @GetMapping("/work/{id}")
    public Result<ClassifyVo> getWorkClassifyById(@PathVariable Integer id){
        return classifyService.getWorkClassifyById(id);
    }


    //TODO 查询某个公司的所有行业

    /**
     * 城市分类
     * @return
     */
    //cityname
    @GetMapping("/cityList")
    @ApiOperation("城市分类")
    public Result<List<CityClassifyVo>> getCitiClassify(){
        return classifyService.getAllCities();
    }

    /**
     * id查询城市分类
     * @param id
     * @return
     */
    @ApiOperation("id查询城市分类")
    @GetMapping("/city/{id}")
    public Result<CityClassifyVo> getCityClazzById(@PathVariable Integer id){
        return classifyService.getCityById(id);
    }

    /**
     * 所有学历要求
     * @return
     */
    //workDegree
    @GetMapping("/workDegreeList")
    @ApiOperation("学历分类")
    public Result<List<WorkDegreeVo>> getWorkDegreeList(){
        return classifyService.getWorkDegreeList();
    }

    /**
     * id查询学历
     * @param id
     * @return
     */
    @GetMapping("/degree/{id}")
    @ApiOperation("id查询学历")
    public Result<WorkDegreeVo> getDegreeById(@PathVariable Integer id){
        return classifyService.getDegreeById(id);
    }

    /**
     * 公司规模分类
     * @return
     */
    @GetMapping("/scaleList")
    @ApiOperation("公司规模分类")
    //brandScaleName
    public Result<List<BrandScaleNameVo>> getScaleList(){
        return classifyService.getScaleList();
    }

    /**
     * id查询规模分类
     * @param id
     * @return
     */
    @GetMapping("/scale/{id}")
    @ApiOperation("id查询规模分类")
    public Result<BrandScaleNameVo> getScale(@PathVariable Integer id){
        return classifyService.getScaleById(id);
    }

    /**
     * 工作经验分类
     * @return
     */
    @GetMapping("/expList")
    @ApiOperation("工作经验分类")
    //workExperience
    public Result<List<WorkExperienceVo>> experienceList(){
        return classifyService.getExpList();
    }

    /**
     * id查询exp分类
     * @param id
     * @return
     */
    @GetMapping("/exp/{id}")
    @ApiOperation("id查询exp分类")
    public Result<WorkExperienceVo> getExpById(@PathVariable Integer id){
        return classifyService.getExpById(id);
    }

    /**
     * 薪资分类
     * @return
     */
    @GetMapping("/salaryList")
    @ApiOperation("工作经验分类")
    //workExperience
    public Result<List<WorkSalaryVo>> salaryList(){
        return classifyService.getSalaryList();
    }

    /**
     * id查询薪资分类
     * @param id
     * @return
     */
    @GetMapping("/salary/{id}")
    @ApiOperation("id查询exp分类")
    public Result<WorkSalaryVo> getSalaryById(@PathVariable Integer id){
        return classifyService.getSalaryById(id);
    }
}