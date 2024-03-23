package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.UserCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (UserCompany)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-05 21:25:12
 */
@Mapper
public interface UserCompanyMapper extends BaseMapper<UserCompany> {

    /**
     * 判断当前用户是否是该公司的职位发布者
     * @param userId,workId
     * @return
     */
    Integer judgePriByUserId(@Param("userId") Integer userId, @Param("workId") Integer workId);
}

