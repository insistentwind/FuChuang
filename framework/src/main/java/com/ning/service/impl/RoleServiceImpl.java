package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.Menu;
import com.ning.domain.result.Result;
import com.ning.domain.vo.TreeSelectVo;
import com.ning.mapper.RoleMapper;
import com.ning.domain.entity.Role;
import com.ning.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ning.service.RoleService;

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
        wrapper.eq(Menu::getParentId,0L);
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
     * 查询所有的
     * @param parentId
     * @return
     */
    private List<TreeSelectVo> getAllChildren(Long parentId) {

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

