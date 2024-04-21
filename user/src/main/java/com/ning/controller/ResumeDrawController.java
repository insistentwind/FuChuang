package com.ning.controller;

import com.ning.domain.entity.ResumeDraw;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeDrawVo;
import com.ning.service.ResumeDrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: qjn
 * @create: 2024/04/11 17:15
 **/
@RestController
@Slf4j
@RequestMapping("/resumeDraw")
@Api(tags = "简历画像接口")
public class ResumeDrawController {
    @Autowired
    private ResumeDrawService resumeDrawService;


    /**
     * 根据简历id查询对应画像
     * @param resumeId
     * @return
     */
    @ApiOperation("根据简历id查询对应画像")
    @GetMapping("/get/{id}")
    public Result<ResumeDrawVo> getDrawById(@PathVariable(value = "id") Integer resumeId){
        return resumeDrawService.getDrawById(resumeId);
    }

    /**
     * 删除画像
     * @param resumeId
     * @return
     */
    @ApiOperation("删除画像")
    @DeleteMapping("/{id}")
    public Result<String> deleteDrawById(@PathVariable(value = "id") Integer resumeId){
        return resumeDrawService.delete(resumeId);
    }

    /**
     * 插入对应简历画像
     * @param resumeDrawVo
     * @return
     */
    @ApiOperation("插入对应简历画像")
    @PostMapping("/insert")
    public Result<String> insertDrawById(@RequestBody ResumeDrawVo resumeDrawVo){
        return resumeDrawService.insert(resumeDrawVo);
    }


    /**
     * 更新简历画像
     * @param resumeDrawVo
     * @return
     */
    @ApiOperation("更新简历画像")
    @PutMapping("/update")
    public Result<String> updateDrawById(@RequestBody ResumeDrawVo resumeDrawVo){
        return resumeDrawService.insert(resumeDrawVo);
    }

//    /**
//     * 删除画像
//     * @param resumeId
//     * @return
//     */
//    @ApiOperation("删除画像")
//    @DeleteMapping("/{id}")
//    public Result<String> deleteDrawById(@PathVariable(value = "id") Integer resumeId){
//        return resumeDrawService.delete(resumeId);
//    }
}