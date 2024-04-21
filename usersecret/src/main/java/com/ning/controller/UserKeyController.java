package com.ning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.domain.entity.UserKey;
import com.ning.service.UserKeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author: qjn
 * @create: 2024/04/03 19:08
 **/
@RestController
@Slf4j
@RequestMapping("")
@Api(tags = "密钥端接口")
public class UserKeyController {
    @Resource(name = "userKeyService")
    private UserKeyService userKeyService;

    /**
     * 根据id取得此用户的密钥
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id取得此用户的密钥")
    public UserKey getUserKey(@PathVariable Integer id){
        LambdaQueryWrapper<UserKey> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserKey::getUserId,id);
        return userKeyService.getOne(wrapper);
    }
}