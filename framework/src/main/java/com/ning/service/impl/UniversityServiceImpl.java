package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.University;
import com.ning.mapper.UniversityMapper;
import com.ning.service.UniversityService;
import org.springframework.stereotype.Service;

/**
 * (University)表服务实现类
 *
 * @author makejava
 * @since 2024-04-05 15:18:30
 */
@Service("universityService")
public class UniversityServiceImpl extends ServiceImpl<UniversityMapper, University> implements UniversityService {

}

