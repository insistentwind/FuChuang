package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.ResumeDraw;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历画像(ResumeDraw)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-11 17:09:39
 */
@Mapper
public interface ResumeDrawMapper extends BaseMapper<ResumeDraw> {
    /**
     * 分页查询简历池画像
     * @param pageSize
     * @param offset
     * @return
     */
    List<ResumeDraw> getDrawByPage(@Param(value = "pageSize") Integer pageSize,@Param(value = "offset") Integer offset);

    /**
     * 根据简历id查找对应画像
     * @param resumeId
     * @return
     */
    ResumeDraw getDrawByResumeId(Integer resumeId);
}

