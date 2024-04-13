package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Role;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;

import java.util.List;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2024-03-11 20:38:24
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    List<String> selectRoleKeyById(Integer id);
    /**
     * 获取菜单树接口
     * @return
     */
    Result<List<TreeSelectVo>> treeSelect();
    /**
     * 角色列表分页查询接口
     * @param rolePageVo
     * @return
     */
    Result<PageResult> listByRoleVo(RolePageVo rolePageVo);
    /**
     * 修改角色的停启用状态
     * @return
     */
    Result<String> changeStatus(Long roleId, Integer status);
    /**
     * 新增角色接口
     * @param roleMenuVo
     * @return
     */
    Result<String> insertRole(RoleMenuVo roleMenuVo);
    /**
     * 获取对应角色的权限
     * @return
     */
    Result<RoleMenuTreeSelectVo> roleMenutreeSelect(Integer id);
    /**
     * 角色信息回显接口
     * @param id
     * @return
     */
    Result<RoleMenuVo> getRoleInfoById(Integer id);
    /**
     * 更新角色信息接口
     * @param roleMenuVo
     * @return
     */
    Result<String> updateByRoleMenuVo(RoleMenuVo roleMenuVo);
    /**
     * 删除固定的某个角色（逻辑删除）
     * @param id
     * @return
     */
    Result<String> deleteById(Integer id);
    /**
     * 查询角色列表接口
     * @return
     */
    Result<List<Role>> listAllRole();
}

