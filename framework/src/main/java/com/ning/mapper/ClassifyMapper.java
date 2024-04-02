package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ning.domain.entity.Classify;
import com.ning.domain.vo.ClassifyShowListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Classify)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-21 17:02:23
 */
@Mapper
public interface ClassifyMapper extends BaseMapper<Classify> {
    /**
     * 根据大分类查找中分类
     * @param bigClassify
     * @return
     */
    List<ClassifyShowListVo> listByBigClassify(String bigClassify);


    /**
     * 根据中分类查找小分类
     * @param bigClassify,midClassify
     * @return
     */
    List<ClassifyShowListVo> listByMiddleClassify(@Param("bigClassify") String bigClassify,@Param("midClassify") String midClassify);

    /**
     * 拿到所有的大分类
     * @return
     */
    List<String> getBigClassifyList();
}

