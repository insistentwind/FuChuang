package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Work;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Resume)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-09 23:25:46
 */
@Mapper
public interface WorkMapper extends BaseMapper<Work> {

}

