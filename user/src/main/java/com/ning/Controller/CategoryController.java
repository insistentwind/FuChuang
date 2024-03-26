package com.ning.Controller;

import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.Classify;
import com.ning.domain.entity.Work;
import com.ning.domain.result.Result;
import com.ning.domain.vo.CityClassifyVo;
import com.ning.domain.vo.ClassifyShowListVo;
import com.ning.domain.vo.ClassifyVo;
import com.ning.domain.vo.WorkVo;
import com.ning.service.ClassifyService;
import com.ning.service.WorkService;
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
@Api(tags = "职位分类相关接口")
public class CategoryController {
    @Autowired
    private ClassifyService classifyService;
    @Autowired
    private WorkService workService;



    /**
     * 查询所有的职位分类
     * @return
     */
    @ApiOperation("查询所有的职位分类")
    @GetMapping("/listAllCategory")
    public Result<List<ClassifyShowListVo>> listAllCategory(){
        return classifyService.getNormalCategoryList();
    }


    /**
     * 根据分类id查询所有职位信息
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    @ApiOperation("根据职位分类id查询职位信息")
    public Result<List<WorkVo>> getList(@PathVariable Integer id){
        return workService.getList(id);
    }

    //TODO 查询某个公司的所有行业

    /**
     * 查询城市分类
     * @return
     */
    @GetMapping("/cities")
    @ApiOperation("查询城市分类")
    public Result<List<CityClassifyVo>> getCitiClassify(){
        return classifyService.getAllCities();
    }
}