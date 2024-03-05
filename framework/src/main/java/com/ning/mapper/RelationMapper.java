package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Relation;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Relation)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 15:20:03
 */
@Mapper
public interface RelationMapper extends BaseMapper<Relation> {

}

