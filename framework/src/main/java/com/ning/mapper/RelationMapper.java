package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.Relation;
import com.ning.domain.entity.Work;
import com.ning.domain.vo.WorkPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    Integer getUserByCompany(@Param("companyId") Integer companyId, @Param("userId") Integer userId);


    /**
     * 根据公司id查询发布的职位
     * @return
     */
    List<Work> getWorkByCompanyId(Integer companyId);

    /**
     * 根据分类查询发布的职位
     * @param workPageVo
     * @return
     */
    List<Work> getWorkByCategory(WorkPageVo workPageVo);
}

