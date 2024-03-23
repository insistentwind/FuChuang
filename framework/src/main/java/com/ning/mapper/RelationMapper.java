package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.Relation;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Relation)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 15:20:03
 */
@Mapper
public interface RelationMapper extends BaseMapper<Relation> {
    /**
     * 根据职位id查找公司信息
     * @param id
     * @return
     */
    Company getCompanyByWorkId(Integer id);


    /**
     * 根据用户查询当前公司中是否有其简历
     * @return
     */
    Integer getUserByCompany(Integer companyId, Integer userId);
}

