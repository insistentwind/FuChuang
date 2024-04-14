package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.db02.UNotifyMapper;
import com.ning.domain.entity.UNotify;

import com.ning.service.UNotifyService;
import org.springframework.stereotype.Service;

/**
 * 消息表(UNotify)表服务实现类
 *
 * @author makejava
 * @since 2024-04-14 13:02:49
 */
@Service("uNotifyService")
public class UNotifyServiceImpl extends ServiceImpl<UNotifyMapper, UNotify> implements UNotifyService {

}

