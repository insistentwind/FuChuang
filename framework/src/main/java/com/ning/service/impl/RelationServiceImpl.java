package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.Relation;
import com.ning.mapper.RelationMapper;
import com.ning.service.RelationService;
import org.springframework.stereotype.Service;

/**
 * (Relation)表服务实现类
 *
 * @author makejava
 * @since 2024-03-01 15:20:04
 */
@Service("relationService")
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements RelationService {

}

