package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Resume;
import com.ning.domain.vo.ResumeVo;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;


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
    List<ResumeVo> getInfoByUserId(Integer userId);

    ResumeVo getInfoByResumeId(Integer resumeId);

    /**
     * 设置隐私字段
     * @param userId
     * @param status
     */
    void setObscureByUserId(@Param("userId") Integer userId,@Param("status") Integer status);
}

