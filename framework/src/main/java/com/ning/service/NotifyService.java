package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.entity.Notify;
import com.ning.domain.result.Result;

import java.util.List;

/**
 * (Notify)表服务接口
 *
 * @author makejava
 * @since 2024-02-24 19:34:04
 */
public interface NotifyService extends IService<Notify> {
    /**
     * 新增消息通知
     * @param notifyDto
     * @return
     */
    Result<String> create(NotifyDto notifyDto);

    /**
     * 根据用户名修改消息状态为已读
     * @param notifyDto
     * @return
     */
    Result<String> updateByUsername(NotifyDto notifyDto);
    /**
     * 根据用户名和状态码查询该用户收到的所有通知
     * @param userId
     * @param isRead
     * @return
     */
    Result<List<NotifyDto>> getList(Integer userId, Integer isRead);
}

