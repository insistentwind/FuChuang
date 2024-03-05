package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.UserCompany;
import org.apache.ibatis.annotations.Mapper;

/**
 * (UserCompany)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-05 21:25:12
 */
@Mapper
public interface UserCompanyMapper extends BaseMapper<UserCompany> {

}

