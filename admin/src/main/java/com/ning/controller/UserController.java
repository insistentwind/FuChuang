package com.ning.controller;

import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.WorkLog;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.service.CompanyService;
import com.ning.service.UserService;
import com.ning.service.WorkLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/06 22:32
 **/
@RestController
@Slf4j
@Api(tags = "系统管理员接口")
@RequestMapping("system/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private CompanyService companyService;

    /**
     * 分页查询公司信息
     * @param companyDto
     * @return
     */
    @PreAuthorize(value = "@ps.hasPermission(T(com.ning.constants.SystemConstants).SYSTEM_USER_QUERY)")
    @ApiOperation("分页查询公司信息")
    @GetMapping("/page")
    public Result<PageResult> getHrByPage(CompanyDto companyDto){
        return companyService.getListByDto(companyDto);
    }

    /**
     * 分页条件查询用户信息
     * @param userPageVo
     * @return
     */
    @PreAuthorize(value = "@ps.hasPermission(T(com.ning.constants.SystemConstants).SYSTEM_USER_QUERY)")
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
    @PreAuthorize(value = "@ps.hasPermission(T(com.ning.constants.SystemConstants).SYSTEM_USER_ADD)")
    @ApiOperation("新增系统管理员,可关联角色")
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
    @PreAuthorize(value = "@ps.hasPermission(T(com.ning.constants.SystemConstants).SYSTEM_USER_REMOVE)")
    @ApiOperation("删除固定的某个用户（逻辑删除）")
    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable List<Long> id){
        log.info("删除固定的某个用户（逻辑删除）:{}", id);
        return userService.deleteById(id);
    }


    /**
     * 根据id查询用户信息回显接口
     * @return
     */
    @ApiOperation("id用户信息回显")
    @GetMapping("/{id}")
    public Result<UserRoleInfoVo> getUserInfoById(@PathVariable Integer id){
        log.info("根据id查询用户信息回显接口:{}",id);
        return userService.getUserInfoById(id);
    }


    /**
     * 更新用户信息接口
     * @param userRoleVo
     * @return
     */
    @ApiOperation("更新用户信息")
    @PutMapping
    public Result<String> updateUserInfo(@RequestBody UserRoleVo userRoleVo){
        log.info("更新用户信息接口:{}",userRoleVo);
        return userService.updateUserRoleVo(userRoleVo);
    }



    /**
     * 批量删除公司
     * @param ids
     * @return
     */
    @ApiOperation("批量删除公司")
    @DeleteMapping
    public Result<String> deleteByIds(List<Integer> ids){
        return companyService.deleteByIds(ids);
    }

}