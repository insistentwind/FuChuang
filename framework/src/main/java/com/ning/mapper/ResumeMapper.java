package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Resume;
import com.ning.domain.vo.ResumeVo;
import org.mapstruct.Mapper;


/**
 * (Resume)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-24 23:48:37
 */
@Mapper
public interface ResumeMapper extends BaseMapper<Resume> {
    /**
     * 根据简历id拿到简历内容以及简历所属的用户id
     * @param userId
     * @return
     */
    ResumeVo getInfoByUserId(Integer userId);

    ResumeVo getInfoByResumeId(Integer resumeId);
}

