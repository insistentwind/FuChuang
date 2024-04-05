package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.WorkLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 职位操作记录表(WorkLog)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-05 17:31:03
 */
@Mapper
public interface WorkLogMapper extends BaseMapper<WorkLog> {

}

