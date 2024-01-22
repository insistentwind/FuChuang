package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.dto.ResumeVo;
import com.ning.domain.entity.History;
import com.ning.domain.entity.Resume;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * (History)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-20 21:58:08
 */
@Mapper
public interface HistoryMapper extends BaseMapper<History> {

    /**
     * 多表联查，根据用户id查询其历史记录
     * @param id
     * @return
     */
//    SELECT resume.* FROM history LEFT JOIN resume ON resume_id=history.resume_id where user_id = #{id}
    List<ResumeVo> getListByUserId(Integer id);

}

