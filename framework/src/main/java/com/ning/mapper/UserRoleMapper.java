package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * (UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-11 21:40:47
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

