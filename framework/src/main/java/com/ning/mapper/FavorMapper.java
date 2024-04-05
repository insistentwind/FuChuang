package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Favor;
import com.ning.domain.entity.Work;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * (Favor)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-01 18:01:17
 */
@Mapper
public interface FavorMapper extends BaseMapper<Favor> {

    Favor getByFavor(Favor favor);
    /**
     * 根据id查询该用户的所有收藏
     * @param id
     * @return
     */
    List<Work> getAllFavorByUserId(Integer id);
    /**
     * 根据职位id查询该用户是否收藏
     * @param workId
     * @return
     */
    Work getFavorByUserAndWorkId(@Param("userId") Integer userId,@Param("workId") Integer workId);
}

