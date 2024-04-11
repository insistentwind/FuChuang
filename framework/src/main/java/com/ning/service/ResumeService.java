package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.dto.ResumePageDto;
import com.ning.domain.entity.Resume;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeVo;

import java.util.List;


/**
 * (Resume)表服务接口
 *
 * @author makejava
 * @since 2024-01-24 23:48:37
 */
public interface ResumeService extends IService<Resume> {
    /**
     * 查询公共简历池数据
     * @param resumePageDto
     * @return
     */
    Result<List<ResumeVo>> getPage(ResumePageDto resumePageDto);
    /**
     * 设置简历是否放入公共池
     * @param id
     * @return
     */
    Result<String> setNoPool(Integer id);
    /**
     * 根据id查询简历数据
     * @param resumeId
     * @return
     */
    Result<ResumeVo> getResumeVoByResumeId(Integer resumeId);
}

