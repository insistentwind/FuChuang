package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.WorkDegreeMapper;
import com.ning.domain.entity.WorkDegree;
import com.ning.service.WorkDegreeService;
import org.springframework.stereotype.Service;

/**
 * (WorkDegree)表服务实现类
 *
 * @author makejava
 * @since 2024-03-28 17:04:10
 */
@Service("workDegreeService")
public class WorkDegreeServiceImpl extends ServiceImpl<WorkDegreeMapper, WorkDegree> implements WorkDegreeService {

}

