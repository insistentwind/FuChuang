package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Company;
import org.mapstruct.Mapper;

/**
 * (Company)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-15 18:09:03
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

}

