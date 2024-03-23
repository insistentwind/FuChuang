package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.WorkUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * (WorkUser)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-19 22:40:04
 */
@Mapper
public interface WorkUserMapper extends BaseMapper<WorkUser> {

}

