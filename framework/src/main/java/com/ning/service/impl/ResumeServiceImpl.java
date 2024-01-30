package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.Resume;
import com.ning.mapper.ResumeMapper;
import com.ning.service.ResumeService;
import org.springframework.stereotype.Service;

/**
 * (Resume)表服务实现类
 *
 * @author makejava
 * @since 2024-01-24 23:48:37
 */
@Service("resumeService")
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

}

