package com.ning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.annotation.SecurityParameter;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.FavorDto;
import com.ning.domain.entity.Favor;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;
import com.ning.service.FavorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2024/2/1 18:03
 */
@RestController
@Slf4j
@Api(tags = "收藏职位")
@RequestMapping("/favor")
public class FavorController {
    @Autowired
    private FavorService favorService;

    /**
     * 添加收藏
     * @param favorDto
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @PostMapping
    @ApiOperation("添加收藏")
    public Result<String> create(@RequestBody FavorDto favorDto){
        log.info("添加收藏信息:{}",favorDto);
        Favor entity = find(favorDto);
        if(entity != null){
            //说明已经被收藏了
            throw new RuntimeException("该职位已被收藏");
        }
        return favorService.create(favorDto);
    }

    /**
     * 检查此职位是否已经被收藏
     * @param favorDto
     * @return
     */
    public Favor find(FavorDto favorDto){
        Integer userId = favorDto.getUserId();
        Integer workId = favorDto.getWorkId();
        LambdaQueryWrapper<Favor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favor::getUserId,userId)
                        .eq(Favor::getWorkId,workId);
        return favorService.getOne(wrapper);
    }

    /**
     * 取消收藏
     * @param favorDto
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @DeleteMapping("/cancel")
    @ApiOperation("取消收藏")
    public Result<String> unFavor(FavorDto favorDto){
        log.info("取消收藏:{}",favorDto);
        return favorService.unFavor(favorDto);
    }

    /**
     * 查询该用户的所有收藏
     * @return
     */
    @SecurityParameter(outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @GetMapping("/user")
    @ApiOperation("查询该用户的所有收藏")
    public Result<List<WorkVo>> getAllFavors(){
        return favorService.getAllFavors();
    }

    /**
     * 根据职位id查询该用户是否收藏
     * @param workId
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @GetMapping("/{workId}")
    @ApiOperation("根据职位id查询该用户是否收藏")
    public Result<String> getFavorByWorkId(@PathVariable Integer workId) {
        return favorService.getListByWorkId(workId);
    }
}
