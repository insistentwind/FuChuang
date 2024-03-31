package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Announce;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表(Announce)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-31 17:49:13
 */
@Mapper
public interface AnnounceMapper extends BaseMapper<Announce> {

}

