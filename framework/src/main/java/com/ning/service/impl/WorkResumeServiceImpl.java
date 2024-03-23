package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.WorkResumeMapper;
import com.ning.domain.entity.WorkResume;
import org.springframework.stereotype.Service;
import com.ning.service.WorkResumeService;

/**
 * (WorkResume)表服务实现类
 *
 * @author makejava
 * @since 2024-03-19 20:06:38
 */
@Service("workResumeService")
public class WorkResumeServiceImpl extends ServiceImpl<WorkResumeMapper, WorkResume> implements WorkResumeService {

}

