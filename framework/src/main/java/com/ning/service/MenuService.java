package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Menu;
import com.ning.domain.result.Result;
import com.ning.domain.vo.MenuVo;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-03-11 20:38:06
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id查询权限信息
     *
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Integer id);

    /**
     * 查询menu结果是tree的形式，也就是子父菜单
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Integer userId);
    /**
     * 展示菜单列表，不需要分页
     * @return
     */
    Result<List<Menu>> listByWrapper(String menuName, Integer visible);
    /**
     * 新增菜单
     * @param menu
     * @return
     */
    Result<String> insertMenu(Menu menu);
    /**
     * 根据id查询菜单数据
     */
    Result<Menu> showMenuById(Long id);
    /**
     * 更新菜单
     * @param menu
     * @return
     */
    Result<String> updateByEntity(Menu menu);
    /**
     * 删除菜单
     * @param id
     * @return
     */
    Result<String> deleteById(Long id);
}

