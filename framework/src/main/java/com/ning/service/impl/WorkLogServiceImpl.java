package com.ning.service.impl;

import com.alibaba.druid.filter.AutoLoad;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.Work;
import com.ning.domain.entity.WorkLog;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkLogVo;
import com.ning.exception.BaseException;
import com.ning.mapper.WorkLogMapper;
import com.ning.service.WorkLogService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职位操作记录表(WorkLog)表服务实现类
 *
 * @author makejava
 * @since 2024-04-05 17:31:07
 */
@Service("workLogService")
public class WorkLogServiceImpl extends ServiceImpl<WorkLogMapper, WorkLog> implements WorkLogService {
    @Autowired
    private WorkLogMapper workLogMapper;

    /**
     * 获取操作失败的职位list
     * @param workLogVo
     * @return
     */
    @Override
    public Result<List<WorkLog>> getListByVo(WorkLogVo workLogVo) {
        LambdaQueryWrapper<WorkLog> wrapper = new LambdaQueryWrapper<>();
        if (workLogVo != null){
            wrapper.eq(workLogVo.getWorkId() != null,WorkLog::getWorkId,workLogVo.getWorkId())
                    .eq(workLogVo.getTagFlag() != null,WorkLog::getTagFlag,workLogVo.getTagFlag());
            List<WorkLog> list = this.list(wrapper);

            this.remove(wrapper);
            return Result.success(list);
        }
        List<WorkLog> list = this.list();
        //删除全部数据
        this.remove(wrapper);

        return Result.success(list);
    }
    /**
     * 根据id获取职位信息
     * @param workId
     * @return
     */
    @Override
    public Result<List<WorkLog>> getListByWorkId(Integer workId) {
        if (workId == null){
            throw new BaseException("请检查输入");
        }
        LambdaQueryWrapper<WorkLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkLog::getWorkId,workId);
        List<WorkLog> list = this.list(wrapper);
        if (list.size() == 0){
            throw new BaseException("结果为空");
        }
        return Result.success(list);
    }
    /**
     * 查看职位操作日志
     */
    @Override
    public Result<List<WorkLog>> getWorkLogs() {
        List<WorkLog> list = this.list();
        return Result.success(list);
    }
    /**
     * 批量删除日志信息
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Result<String> deleteBatch(List<Integer> ids) {
        deleteBatch(ids);
        return Result.success(SystemConstants.SUCCESS);
    }
    /**
     * 根据vo修改日志信息
     * @param workLogVo
     * @return
     */
    @Override
    public Result<String> updateByVo(WorkLogVo workLogVo) {
        WorkLog workLog = BeanCopyUtils.copyBean(workLogVo, WorkLog.class);
        updateById(workLog);
        return Result.success(SystemConstants.SUCCESS);
    }
}

