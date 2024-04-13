package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.SimilarPosition;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;

import java.util.List;

/**
 * (SimilarPosition)表服务接口
 *
 * @author makejava
 * @since 2024-04-12 19:09:46
 */
public interface SimilarPositionService extends IService<SimilarPosition> {
    /**
     * 传入职位id查找相似职位
     * @param workId
     * @return
     */
    Result<List<WorkVo>> getSimilarByWorkId(Integer workId);
}

