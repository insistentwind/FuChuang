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
    /**
     * 根据id获取职位信息
     * @param workId
     * @return
     */
    Result<List<WorkLog>> getListByWorkId(Integer workId);
    /**
     * 查看职位操作日志
     */
    Result<List<WorkLog>> getWorkLogs();
    /**
     * 批量删除日志信息
     * @param ids
     * @return
     */
    Result<String> deleteBatch(List<Integer> ids);
    /**
     * 根据vo修改日志信息
     * @param workLogVo
     * @return
     */
    Result<String> updateByVo(WorkLogVo workLogVo);
}

