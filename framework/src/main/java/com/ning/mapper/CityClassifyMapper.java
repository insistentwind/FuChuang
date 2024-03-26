package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.CityClassify;
import org.apache.ibatis.annotations.Mapper;

/**
 * 城市分类(CityClassify)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-26 16:33:33
 */
@Mapper
public interface CityClassifyMapper extends BaseMapper<CityClassify> {

}

