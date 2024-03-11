package com.ning.controller;

import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserPageVo;
import com.ning.domain.vo.UserRoleVo;
import com.ning.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/06 22:32
 **/
@RestController
@Slf4j
@Api(tags = "管理端用户相关接口")
@RequestMapping("system/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页条件查询用户信息
     * @param userPageVo
     * @return
     */
    @ApiOperation("分页条件查询用户信息")
    @GetMapping("/list")
    public Result<PageResult> page(UserPageVo userPageVo){
        log.info("用户列表分页查询：{}",userPageVo);
        return userService.pageByUserPageVo(userPageVo);
    }

    /**
     * 新增用户
     * @return
     */
    @ApiOperation("新增用户")
    @PostMapping
    public Result<String> insertUser(@RequestBody UserRoleVo userRoleVo){
        log.info("新增用户:{}",userRoleVo);
        return userService.insertByUserRoleVo(userRoleVo);
    }
    /**
     * 删除固定的某个用户（逻辑删除）
     * @param id
     * @return
     */
    @ApiOperation("删除固定的某个用户（逻辑删除）")
    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable List<Long> id){
        log.info("删除固定的某个用户（逻辑删除）:{}", id);
        return userService.deleteById(id);
    }
}