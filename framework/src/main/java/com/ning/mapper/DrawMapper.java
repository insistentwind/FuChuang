package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Draw;
import org.apache.ibatis.annotations.Mapper;

/**
 * 职位画像(Drwa)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-09 18:23:34
 */
@Mapper
public interface DrawMapper extends BaseMapper<Draw> {

}

