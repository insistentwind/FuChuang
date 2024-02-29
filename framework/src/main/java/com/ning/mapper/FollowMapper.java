package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Follow;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * (Follow)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-24 18:08:36
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

}

