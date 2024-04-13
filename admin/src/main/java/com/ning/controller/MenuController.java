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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/11 21:48
 **/
@RestController
@Slf4j
@RequestMapping("/system/menu")
@Api(tags = "后台菜单接口")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    /**
     * 展示菜单列表，不需要分页
     * @return
     */
    @ApiOperation("条件查询菜单列表,不是树")
    @GetMapping("/list")
    public Result<List<Menu>> list(@RequestParam(required = false) String menuName,
                                   @RequestParam(required = false,defaultValue = "1") Integer visible){
        log.info("查询菜单列表：菜单名{},状态{}",menuName,visible);
        return menuService.listByWrapper(menuName,visible);
    }

    /**
     * 菜单树接口
     * @return
     */
    @GetMapping("/treeselect")
    @ApiOperation("菜单树接口")
    public Result<List<TreeSelectVo>> treeSelect(){
        log.info("查询所有的权限");
        return roleService.treeSelect();
    }


    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @ApiOperation("新增菜单")
    @PostMapping
    public Result<String> insertMenu(@RequestBody Menu menu){
        log.info("新增菜单：{}",menu);
        return menuService.insertMenu(menu);
    }
    /**
     * 根据id查询菜单数据
     */
    @ApiOperation("根据id查询菜单数据")
    @GetMapping("/{id}")
    public Result<Menu> showMenuById(@PathVariable Long id){
        log.info("根据id查询菜单数据：{}",id);
        return menuService.showMenuById(id);
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @ApiOperation("更新菜单")
    @PutMapping
    public Result<String> updateByEntity(@RequestBody Menu menu){
        log.info("更新菜单的内容：{}",menu);
        return menuService.updateByEntity(menu);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除菜单")
    public Result<String> deleteMenu(@PathVariable Long id){
        log.info("删除菜单:{}",id);
        return menuService.deleteById(id);
    }
}