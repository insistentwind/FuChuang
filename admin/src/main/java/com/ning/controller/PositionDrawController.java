package com.ning.controller;

import com.ning.constants.SystemConstants;
import com.ning.domain.entity.Draw;
import com.ning.domain.result.Result;
import com.ning.domain.vo.DrawVo;
import com.ning.service.DrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/04/12 20:03
 **/
@RestController
@Slf4j
@RequestMapping("/system/draw")
@Api(tags = "职位画像接口")
public class PositionDrawController {
    @Autowired
    private DrawService drawService;

    /**
     * 根据职位id找职位画像
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据职位id找职位画像")
    public Result<DrawVo> getDrawVoByWorkId(@PathVariable Integer id){
        return drawService.getDrawVoByWorkId(id);
    }

    /**
     * 新增职位画像
     * @param draw
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("新增职位画像")
    public Result<String> insertDraw(@RequestBody Draw draw){
        return drawService.insertByEntity(draw);
    }

    /**
     * 修改职位画像
     * @param draw
     * @return
     */
    @ApiOperation("修改职位画像")
    @PutMapping("/update")
    public Result<String> updateDraw(@RequestBody Draw draw){
        return drawService.updateByEntity(draw);
    }

    /**
     * 删除职位画像
     * @return
     */
    @ApiOperation("删除职位画像")
    @DeleteMapping("/delete/{ids}")
    @Transactional
    public Result<String> deleteDraw(@PathVariable List<Integer> ids){
        drawService.removeBatchByIds(ids);
        return Result.success(SystemConstants.SUCCESS);
    }

}