package com.ning.Controller;

import com.ning.domain.entity.Classify;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ClassifyShowListVo;
import com.ning.domain.vo.ClassifyVo;
import com.ning.service.ClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 查询所有的分类
     * @return
     */
    @ApiOperation("查询所有的分类")
    @GetMapping("/listAllCategory")
    public Result<List<ClassifyShowListVo>> listAllCategory(){
        return classifyService.getNormalCategoryList();
    }


}