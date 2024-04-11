package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.ResumeDraw;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeDrawVo;

/**
 * 简历画像(ResumeDraw)表服务接口
 *
 * @author makejava
 * @since 2024-04-11 17:09:40
 */
public interface ResumeDrawService extends IService<ResumeDraw> {
    /**
     * 根据简历id查询对应画像
     * @param resumeId
     * @return
     */
    Result<ResumeDrawVo> getDrawById(Integer resumeId);
    /**
     * 插入对应简历画像
     * @param resumeDrawVo
     * @return
     */
    Result<String> insert(ResumeDrawVo resumeDrawVo);
    /**
     * 删除画像
     * @param resumeId
     * @return
     */
    Result<String> delete(Integer resumeId);
}

