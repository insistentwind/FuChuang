package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.Menu;
import com.ning.domain.entity.RoleMenu;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.mapper.RoleMapper;
import com.ning.domain.entity.Role;
import com.ning.service.MenuService;
import com.ning.service.RoleMenuService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ning.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2024-03-11 20:38:25
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;
    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    @Override
    public List<String> selectRoleKeyById(Integer id) {
        //如果是管理员，那么返回的集合中只需要有admin
        if(id == 1){
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Role::getId,1);
            List<Role> list = list(wrapper);
            return list.stream().map(Role::getRoleKey).collect(Collectors.toList());
        }
        return roleMapper.selectRoleKeyById(id);
    }
    /**
     * 获取菜单树接口
     * @return
     */
    @Override
    public Result<List<TreeSelectVo>> treeSelect() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,0);
        //这里获取到最高等级的父权限
        List<Menu> highestMenuList = menuService.list(wrapper);

        List<TreeSelectVo> collect = highestMenuList.stream()
                .map(item -> {
                    TreeSelectVo treeSelectVo = new TreeSelectVo();
                    treeSelectVo.setLabel(item.getMenuName())
                            .setId(item.getId())
                            .setParentId(item.getParentId());

                    treeSelectVo.setChildren(getAllChildren(treeSelectVo.getId()));
                    return treeSelectVo;
                })
                .collect(Collectors.toList());
        return Result.success(collect);
    }
    /**
     * 角色列表分页查询接口
     * @param rolePageVo
     * @return
     */
    @Override
    public Result<PageResult> listByRoleVo(RolePageVo rolePageVo) {
        Page page = new Page(rolePageVo.getPageNum(), rolePageVo.getPageSize(),false);
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Role::getRoleSort);
        wrapper.like(rolePageVo.getRoleName() != null,Role::getRoleName,rolePageVo.getRoleName()).eq(rolePageVo.getStatus() != null,Role::getStatus,rolePageVo.getStatus());
        page(page,wrapper);
        return Result.success(new PageResult(page.getRecords().size(),page.getRecords()));
    }
    /**
     * 修改角色的停启用状态
     * @return
     */
    @Override
    public Result<String> changeStatus(Integer roleId, Integer status) {
        Role role = new Role();
        role.setId(roleId);
        role.setStatus(status);
        updateById(role);
        return Result.success("修改成功");
    }
    /**
     * 新增角色接口
     * @param roleMenuVo
     * @return
     */
    @Override
    @Transactional
    public Result<String> insertRole(RoleMenuVo roleMenuVo) {
        Role role = BeanCopyUtils.copyBean(roleMenuVo, Role.class);
        roleMapper.insert(role);
        Integer roleId = role.getId();
        List<String> menuIds = roleMenuVo.getMenuIds();
        menuIds.stream().forEach(item -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);

            roleMenu.setMenuId(Integer.valueOf(item));
            roleMenuService.save(roleMenu);
        });
        return Result.success("新增角色成功");
    }
    /**
     * 获取对应角色的权限
     * @return
     */
    @Override
    public Result<RoleMenuTreeSelectVo> roleMenutreeSelect(Integer id) {
        //如果是管理员
        if(id.equals(1)){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Menu::getParentId,0);
            //这里获取到最高等级的父权限
            List<Menu> highestMenuList = menuService.list(wrapper);
            List<TreeSelectVo> collect = highestMenuList.stream()
                    .map(item -> {
                        TreeSelectVo treeSelectVo = TreeSelectVo.builder()
                                .id(item.getId())
                                .label(item.getMenuName())
                                .parentId(item.getParentId())
                                .build();
                        treeSelectVo.setChildren(ListByParentId(item.getId()));
                        return treeSelectVo;
                    })
                    .collect(Collectors.toList());

//            List<String> checkedKeys = new ArrayList<>();
//            LambdaQueryWrapper<RoleMenu> wrapper1 = new LambdaQueryWrapper<>();
//            wrapper1.select(RoleMenu::getMenuId);
//            List<RoleMenu> list = roleMenuService.list(wrapper1);
            LambdaQueryWrapper<Menu> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.select(Menu::getId);
            List<Menu> list = menuService.list(wrapper1);
            // checkedKey是这个角色所拥有的菜单id
            List<String> checkedKeys = list.stream()
                    .map(item -> item.getId().toString())
                    .collect(Collectors.toList());
            return Result.success(new RoleMenuTreeSelectVo(collect,checkedKeys));
        }
        //这里不是管理员
        //TODO 这里刚设置重新设置好用户的id，后续要进行用户的权限查询
        else{
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Menu::getParentId,0);
            //这里获取到最高等级的父权限
            List<Menu> highestMenuList = menuService.list(wrapper);
            List<TreeSelectVo> collect = highestMenuList.stream()
                    .map(item -> {
                        TreeSelectVo treeSelectVo = TreeSelectVo.builder()
                                .id(item.getId())
                                .label(item.getMenuName())
                                .parentId(item.getParentId())
                                .build();
                        treeSelectVo.setChildren(ListByParentId(item.getId()));
                        return treeSelectVo;
                    })
                    .collect(Collectors.toList());

            LambdaQueryWrapper<RoleMenu> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(RoleMenu::getRoleId,id);
            List<RoleMenu> rolemenus = roleMenuService.list(wrapper1);

            List<String> checkedKeys = new ArrayList<>();
            for (RoleMenu rolemenu : rolemenus) {
                Integer menuId = rolemenu.getMenuId();
                checkedKeys.add(menuId.toString());
            }

            return Result.success(new RoleMenuTreeSelectVo(collect,checkedKeys));
        }
    }
    /**
     * 角色信息回显接口
     * @param id
     * @return
     */
    @Override
    public Result<RoleMenuVo> getRoleInfoById(Integer id) {
        Role role = roleMapper.selectById(id);
        return Result.success(BeanCopyUtils.copyBean(role,RoleMenuVo.class));
    }
    /**
     * 更新角色信息接口
     * @param roleMenuVo
     * @return
     */
    @Transactional
    @Override
    public Result<String> updateByRoleMenuVo(RoleMenuVo roleMenuVo) {
        Role role = BeanCopyUtils.copyBean(roleMenuVo, Role.class);
        roleMapper.updateById(role);

        //先把roleMenu表中的所有对应id信息全部删除
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,roleMenuVo.getId());
        roleMenuService.remove(wrapper);

        Integer roleId = role.getId();
        List<String> menuIds = roleMenuVo.getMenuIds();
        menuIds.forEach(item -> {
            Integer menuId = Integer.valueOf(item);
            RoleMenu roleMenu = new RoleMenu(roleId,menuId);
            roleMenuService.save(roleMenu);
        });
        return Result.success("更新成功");
    }
    /**
     * 删除固定的某个角色（逻辑删除）
     * @param id
     * @return
     */
    @Override
    public Result<String> deleteById(Integer id) {
        roleMenuService.removeById(id);
        return Result.success("删除成功");
    }

    /**
     * 查询角色列表接口
     * @return
     */
    @Override
    public Result<List<Role>> listAllRole() {
        List<Role> list = list();
        return Result.success(list);
    }

    /**
     * 查询parentid是menu的子节点集合
     * @param
     * @return
     */
    private List<TreeSelectVo> ListByParentId(Integer parentId) {


        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,parentId);
        //根据parentId查询对应的menu列表
        List<Menu> menuList = menuService.list(wrapper);
//        List<Menu> menuList = menuService.list();

        List<TreeSelectVo> collect = menuList.stream()
//                .filter(o -> o.getParentId().equals(parentId)
                .map(item -> {
                    TreeSelectVo treeSelectVo = new TreeSelectVo();
                    treeSelectVo.setId(item.getId())
                            .setParentId(item.getParentId())
                            .setLabel(item.getMenuName());
                    treeSelectVo.setChildren(ListByParentId(item.getId()));
                    return treeSelectVo;
                }).collect(Collectors.toList());

        return collect;
    }

    /**
     * 查询所有的
     * @param parentId
     * @return
     */
    private List<TreeSelectVo> getAllChildren(Integer parentId) {

        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,parentId);
        //根据parentId查询对应的menu列表
        List<Menu> menuList = menuService.list(wrapper);
        //进行封装
        List<TreeSelectVo> collect = menuList.stream().map(item -> {
            TreeSelectVo treeSelectVo = new TreeSelectVo();
            treeSelectVo.setParentId(item.getParentId())
                    .setLabel(item.getMenuName())
                    .setId(item.getId())
                    .setChildren(getAllChildren(item.getId()));
            return treeSelectVo;
        }).collect(Collectors.toList());
        return collect;
    }
}

