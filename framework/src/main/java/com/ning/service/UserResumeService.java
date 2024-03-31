package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.UserResume;
import com.ning.domain.vo.ResumeVo;

import java.util.List;

/**
 * (UserResume)表服务接口
 *
 * @author makejava
 * @since 2024-03-14 21:38:21
 */
public interface UserResumeService extends IService<UserResume> {
    /**
     * 当前用户所创建的简历列表
     * @return
     */
    List<ResumeVo> getListByUserId(Integer id);
}

