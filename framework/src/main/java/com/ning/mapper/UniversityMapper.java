package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.University;
import org.apache.ibatis.annotations.Mapper;

/**
 * (University)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-05 15:18:24
 */
@Mapper
public interface UniversityMapper extends BaseMapper<University> {

}

