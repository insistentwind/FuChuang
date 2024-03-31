package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.History;
import com.ning.domain.result.Result;
import com.ning.domain.vo.HistoryVo;
import com.ning.domain.vo.WorkVo;

import java.util.List;


/**
 * (History)表服务接口
 *
 * @author makejava
 * @since 2024-01-20 21:58:09
 */
public interface HistoryService extends IService<History> {
    /**
     * 查询当前用户的浏览历史
     * @return
     */
    Result<List<HistoryVo>> getHistoryByUser();
    /**
     * 根据历史记录id查询职位详细信息
     * @param id
     * @return
     */
    Result<WorkVo> getHistoryById(Integer id);
}

