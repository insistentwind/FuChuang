package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.WorkLog;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkLogVo;
import com.ning.mapper.WorkLogMapper;
import com.ning.service.WorkLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职位操作记录表(WorkLog)表服务实现类
 *
 * @author makejava
 * @since 2024-04-05 17:31:07
 */
@Service("workLogService")
public class WorkLogServiceImpl extends ServiceImpl<WorkLogMapper, WorkLog> implements WorkLogService {
    /**
     * 获取操作失败的职位list
     * @param workLogVo
     * @return
     */
    @Override
    public Result<List<WorkLog>> getListByVo(WorkLogVo workLogVo) {
        if (workLogVo != null){
            LambdaQueryWrapper<WorkLog> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(workLogVo.getWorkId() != null,WorkLog::getWorkId,workLogVo.getWorkId())
                    .eq(workLogVo.getTagFlag() != null,WorkLog::getTagFlag,workLogVo.getTagFlag());

            return Result.success(this.list(wrapper));
        }

        return Result.success(this.list());
    }
}

