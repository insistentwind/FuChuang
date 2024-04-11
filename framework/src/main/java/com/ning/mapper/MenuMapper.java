package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Menu;
import com.ning.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-11 20:38:04
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id查询权限信息
     * @param id
     * @return
     */
    List<String> selectPermsByUserId(Integer id);
    /**
     * 返回所有符合要求的menu
     * @return
     */
    List<Menu> selectAllRouterMenu();

    /**
     * 查到当前用户所具有的menu
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Integer userId);
}

