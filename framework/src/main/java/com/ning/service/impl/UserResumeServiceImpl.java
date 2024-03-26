package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.Resume;
import com.ning.mapper.UserResumeMapper;
import com.ning.domain.entity.UserResume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ning.service.UserResumeService;

import java.util.List;

/**
 * (UserResume)表服务实现类
 *
 * @author makejava
 * @since 2024-03-14 21:38:21
 */
@Service("userResumeService")
public class UserResumeServiceImpl extends ServiceImpl<UserResumeMapper, UserResume> implements UserResumeService {
    @Autowired
    private UserResumeMapper userResumeMapper;

    /**
     * 当前用户所创建的简历列表
     * @return
     */
    @Override
    public List<Resume> getListByUserId(Integer id) {
        return userResumeMapper.getListByUserId(id);
    }
}

