package com.ning.controller;

import com.ning.domain.entity.Role;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.RoleMenuTreeSelectVo;
import com.ning.domain.vo.RoleMenuVo;
import com.ning.domain.vo.RolePageVo;
import com.ning.domain.vo.RoleVo;
import com.ning.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2023/12/2 20:56
 */
@RestController
@Slf4j
@RequestMapping("/system/role")
@Api(tags = "角色接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表分页查询接口
     * @param rolePageVo
     * @return
     */
    @PreAuthorize("@ps.hasPermission('')")
    @ApiOperation("角色列表分页查询接口")
    @GetMapping("/role/list")
    public Result<PageResult> list(RolePageVo rolePageVo){
        log.info("角色列表分页查询接口:{}",rolePageVo);
        return roleService.listByRoleVo(rolePageVo);
    }

    /**
     * 修改角色的停启用状态
     * @return
     */
    @ApiOperation("修改角色的停启用状态")
    @PutMapping("/role/changeStatus")
    public Result<String> changeStatus(@RequestBody RoleVo roleVo){
        Integer roleId = roleVo.getRoleId();
        log.info("修改角色的停启用状态:{}",roleId);
        return roleService.changeStatus(roleId, roleVo.getStatus());
    }
    /**
     * 新增角色接口
     * @param roleMenuVo
     * @return
     */
    @PostMapping("/role")
    @ApiOperation("新增角色接口")
    public Result<String> insertRole(@RequestBody RoleMenuVo roleMenuVo){
        // 新增之前要先拿到菜单树，菜单树在menucontroller中
        log.info("新增角色接口:{}",roleMenuVo);
        return roleService.insertRole(roleMenuVo);
    }
    /**
     * 获取对应角色的权限
     * @return
     */
    @GetMapping("/menu/roleMenuTreeselect/{id}")
    @ApiOperation("获取对应角色的权限")
    public Result<RoleMenuTreeSelectVo> roleMenuTreeSelect(@PathVariable Integer id){
        log.info("获取对应角色的权限:{}",id);
        return roleService.roleMenutreeSelect(id);
    }

    /**
     * 角色信息回显接口
     * @param id
     * @return
     */
    @ApiOperation("角色信息回显接口")
    @GetMapping("/role/{id}")
    public Result<RoleMenuVo> getRoleInfoById(@PathVariable Integer id){
        log.info("角色信息回显接口:{}",id);
        return roleService.getRoleInfoById(id);
    }
    /**
     * 更新角色信息接口
     * @param roleMenuVo
     * @return
     */
    @PutMapping("/role")
    @ApiOperation("更新角色信息接口")
    public Result<String> updateRole(@RequestBody RoleMenuVo roleMenuVo){
        log.info("更新角色信息接口:{}",roleMenuVo);
        return roleService.updateByRoleMenuVo(roleMenuVo);
    }

    /**
     * 删除固定的某个角色（逻辑删除）
     * @param id
     * @return
     */
    @ApiOperation("删除固定的某个角色（逻辑删除）")
    @DeleteMapping("/role/{id}")
    public Result<String> deleteRole(@PathVariable Integer id){
        log.info("删除角色id：{}",id);
        return roleService.deleteById(id);
    }

    /**
     * 查询角色列表接口
     * @return
     */
    @ApiOperation("查询角色列表接口")
    @GetMapping("/role/listAllRole")
    public Result<List<Role>> listAllRole(){
        log.info("查询所有角色列表");
        return roleService.listAllRole();
    }

}