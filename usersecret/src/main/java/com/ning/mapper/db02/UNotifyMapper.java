package com.ning.mapper.db02;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.UNotify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息表(UNotify)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-14 13:02:48
 */
@Mapper
public interface UNotifyMapper extends BaseMapper<UNotify> {

}

