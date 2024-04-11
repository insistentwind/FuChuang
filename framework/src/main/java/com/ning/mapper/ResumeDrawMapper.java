package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.ResumeDraw;
import org.apache.ibatis.annotations.Mapper;

/**
 * 简历画像(ResumeDraw)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-11 17:09:39
 */
@Mapper
public interface ResumeDrawMapper extends BaseMapper<ResumeDraw> {

}

