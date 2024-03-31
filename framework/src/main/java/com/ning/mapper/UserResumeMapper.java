package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.UserResume;
import com.ning.domain.vo.ResumeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (UserResume)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-14 21:38:19
 */
@Mapper
public interface UserResumeMapper extends BaseMapper<UserResume> {
    /**
     * 当前用户所创建的简历列表
     * @return
     */
    List<ResumeVo> getListByUserId(Integer userId);
}

