package com.ning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.entity.UserKey;
import com.ning.service.UserKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: qjn
 * @create: 2024/04/03 19:08
 **/
@RestController
@Slf4j
@RequestMapping("")
public class UserKeyController {
    @Autowired
    private UserKeyService userKeyService;

    /**
     * 根据id取得此用户的密钥
     */
    @GetMapping("/{id}")
    public UserKey getUserKey(@PathVariable Integer id){
        LambdaQueryWrapper<UserKey> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserKey::getUserId,id);
        UserKey one = userKeyService.getOne(wrapper);
//        System.out.println(one);
        return one;
    }
}