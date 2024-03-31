package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.CompanyEmployee;
import com.ning.domain.entity.CompanyUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * (CompanyUser)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-31 16:55:19
 */
@Mapper
public interface CompanyEmployeeMapper extends BaseMapper<CompanyEmployee> {

}

