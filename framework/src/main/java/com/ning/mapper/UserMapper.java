package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.User;
import org.mapstruct.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-16 12:09:21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

