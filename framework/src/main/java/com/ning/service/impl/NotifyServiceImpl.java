package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ning.constants.SystemConstants;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.entity.Notify;
import com.ning.domain.result.Result;
import com.ning.domain.vo.NotifyVo;
import com.ning.mapper.NotifyMapper;
import com.ning.service.NotifyService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * (Notify)表服务实现类
 *
 * @author makejava
 * @since 2024-02-24 19:34:04
 */
@Service("notifyService")
public class NotifyServiceImpl extends ServiceImpl<NotifyMapper, Notify> implements NotifyService {


    /**
     * 新增消息通知
     *
     * @param notifyDto
     * @return
     */
    @Override
    public Result<String> create(NotifyDto notifyDto) {
        Notify notify = BeanCopyUtils.copyBean(notifyDto, Notify.class);
        notify.setTime(LocalDateTime.now());
        save(notify);
        return Result.success("新增通知成功");
    }

    /**
     * 根据用户名修改消息状态为已读
     *
     * @param id
     * @return
     */
    @Override
    public Result<String> updateByUsername(Integer id) {
        Notify notify = new Notify();
        notify.setId(id);
        notify.setIsRead(1);
        notify.setTime(LocalDateTime.now());
        updateById(notify);
        return Result.success();
    }

    /**
     * 根据用户名和状态码查询该用户收到的所有通知
     * @param userId
     * @param isRead
     * @return
     */
    @Override
    public Result<List<NotifyVo>> getList(Integer userId, Integer isRead) {
        LambdaQueryWrapper<Notify> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notify::getUserId, userId)
                .eq(!Objects.equals(isRead, SystemConstants.NOTIFY_STATUS),Notify::getIsRead, isRead);

        List<Notify> list = list(wrapper);
        List<NotifyVo> NotifyVos = BeanCopyUtils.copyBeanList(list, NotifyVo.class);

        return Result.success(NotifyVos);
    }


}

