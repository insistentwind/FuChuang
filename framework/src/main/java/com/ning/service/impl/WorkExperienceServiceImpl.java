package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.mapper.WorkExperienceMapper;
import com.ning.domain.entity.WorkExperience;
import com.ning.service.WorkExperienceService;
import org.springframework.stereotype.Service;

/**
 * (WorkExperience)表服务实现类
 *
 * @author makejava
 * @since 2024-03-28 17:08:04
 */
@Service("workExperienceService")
public class WorkExperienceServiceImpl extends ServiceImpl<WorkExperienceMapper, WorkExperience> implements WorkExperienceService {

}

