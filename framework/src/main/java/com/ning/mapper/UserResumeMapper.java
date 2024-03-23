package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.UserResume;
import org.apache.ibatis.annotations.Mapper;

/**
 * (UserResume)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-14 21:38:19
 */
@Mapper
public interface UserResumeMapper extends BaseMapper<UserResume> {

}

