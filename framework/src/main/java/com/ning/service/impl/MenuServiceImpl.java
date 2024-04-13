package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.result.Result;
import com.ning.domain.vo.MenuVo;
import com.ning.exception.BaseException;
import com.ning.mapper.MenuMapper;
import com.ning.domain.entity.Menu;
import com.ning.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ning.service.MenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-03-11 20:38:06
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据用户id查询权限信息
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermsByUserId(Integer id) {
        try {
            if(SecurityUtils.isAdmin()){
                //返回所有的权限
                LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
                //返回菜单类型是C和F的
                wrapper.in(Menu::getType, SystemConstants.NEW_BUTTON);
                wrapper.eq(Menu::getVisible,SystemConstants.STATUS_NORMAL);
                List<Menu> list = list(wrapper);
                List<String> perms = list.stream().map(Menu::getPerms).collect(Collectors.toList());
//                List<String> menuName = list.stream().map(Menu::getMenuName).collect(Collectors.toList());
                return perms;
            }
        }catch (Exception e){
            throw new BaseException("当前用户未登录!");
        }

        return menuMapper.selectPermsByUserId(id);

    }

    /**
     * 查询menu结果是tree的形式，也就是子父菜单
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Integer userId) {
        List<Menu> menus = null;
        //判断是否是管理员，
        if(SecurityUtils.isAdmin()){
            //如果是，就返回所有符合要求的menu
            menus = menuMapper.selectAllRouterMenu();
        }
        else{
            //否则，查到当前用户所具有的menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找出第一层的菜单，然后去找他们的子菜单设置到children属性汇总
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    /**
     * 展示菜单列表，不需要分页
     * @param menuName
     * @param visible
     * @return
     */
    @Override
    public Result<List<Menu>> listByWrapper(String menuName, Integer visible) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        if(menuName != null){
            wrapper.like(Menu::getName,menuName);
        }
        if(visible != null){
            wrapper.eq(Menu::getVisible,visible);
        }
        List<Menu> menuList = list(wrapper);
        return Result.success(menuList);
    }
    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @Override
    public Result<String> insertMenu(Menu menu) {
        menuMapper.insert(menu);
        return Result.success(SystemConstants.SUCCESS);
    }
    /**
     * 根据id查询菜单数据
     */
    @Override
    public Result<Menu> showMenuById(Long id) {
        Menu menu = menuMapper.selectById(id);
        return Result.success(menu);
    }
    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @Override
    public Result<String> updateByEntity(Menu menu) {
        Menu nowMenu = getById(menu.getId());
        if(nowMenu.getId().equals(menu.getParentId())){
            throw new RuntimeException("修改菜单" + menu.getName()+"失败,上级菜单不能选择自己");
        }
        updateById(menu);
        return Result.success(SystemConstants.SUCCESS);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public Result<String> deleteById(Long id) {
        //查询当前菜单是否有子菜单
        Map map = new HashMap();
        map.put("parent_id",id.toString());
        List list = menuMapper.selectByMap(map);
        if(list.size() > 0 || list != null){
            throw new RuntimeException("存在子菜单不允许删除");
        }
        menuMapper.deleteById(id);
        return Result.success(SystemConstants.SUCCESS);
    }

    /**
     * 构件menuTree
     * @param menus
     * @return
     */
    private List<Menu> builderMenuTree(List<Menu> menus,Long parentId){
        List<Menu> menuTree = menus.stream()
                //满足parentId = parentId就保留
                .filter(menu -> menu.getParentId().equals(parentId)
                        && !Objects.equals(menu.getType(), SystemConstants.NEW_BUTTON))
                .map(item -> {
//                    List<Menu> list = menuMapper.selectChildrenMenuTree();
                    List<Menu> list = getChildren(item, menus);
                    return item.setChildren(list);
                    //最后封装子菜单
                }).collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入参数的子菜单
     *在menus集合中获取的子menu的集合
     * @param item
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu item, List<Menu> menus) {
        List<Menu> children = menus.stream()
                //满足parentId = parentId就保留
                .filter(menu ->menu.getParentId().equals(item.getId())
                        && !Objects.equals(menu.getType(), SystemConstants.NEW_BUTTON))
                //这里处理如果有三级子目录就进行递归的方法找到子目录
                .map(m -> m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return children;
    }
}

