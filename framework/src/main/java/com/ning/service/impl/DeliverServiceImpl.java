package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.Deliver;
import com.ning.mapper.DeliverMapper;
import org.springframework.stereotype.Service;
import com.ning.service.DeliverService;

/**
 * (Deliver)表服务实现类
 *
 * @author makejava
 * @since 2024-03-14 21:48:11
 */
@Service("deliverService")
public class DeliverServiceImpl extends ServiceImpl<DeliverMapper, Deliver> implements DeliverService {

}

