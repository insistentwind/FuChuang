package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.WorkLog;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkLogVo;

import java.util.List;

/**
 * 职位操作记录表(WorkLog)表服务接口
 *
 * @author makejava
 * @since 2024-04-05 17:31:06
 */
public interface WorkLogService extends IService<WorkLog> {
    /**
     * 获取操作失败的职位list
     * @param workLogVo
     * @return
     */
    Result<List<WorkLog>> getListByVo(WorkLogVo workLogVo);
}

