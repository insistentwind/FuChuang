package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Menu;
import com.ning.domain.entity.Role;
import com.ning.domain.result.Result;
import com.ning.domain.vo.TreeSelectVo;

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
}

