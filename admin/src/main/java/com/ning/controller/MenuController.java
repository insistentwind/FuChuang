package com.ning.controller;

import com.ning.domain.entity.Menu;
import com.ning.domain.result.Result;
import com.ning.domain.vo.TreeSelectVo;
import com.ning.service.MenuService;
import com.ning.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/11 21:48
 **/
@RestController
@Slf4j
@RequestMapping("/system/menu")
@Api(tags = "后台菜单相关接口")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    /**
     * 展示菜单列表，不需要分页
     * @return
     */
    @ApiOperation("展示菜单列表，不需要分页")
    @GetMapping("/list")
    public Result<List<Menu>> list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String status){
        log.info("查询菜单列表：菜单名{},状态{}",menuName,status);
        return menuService.listByWrapper(menuName,status);
    }

    /**
     * 获取菜单树接口
     * @return
     */
    @GetMapping("/treeselect")
    @ApiOperation("获取菜单树接口")
    public Result<List<TreeSelectVo>> treeSelect(){
        log.info("查询所有的权限");
        return roleService.treeSelect();
    }
}