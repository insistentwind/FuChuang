package com.ning.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ning.domain.entity.Work;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * (Resume)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-09 23:25:46
 */
@Mapper
public interface WorkMapper extends BaseMapper<Work> {

    long count();

    // 使用MyBatis的分页查询
    default IPage<Work> selectWorkPage(Page<Work> page) {
        return selectPage(page, null);
    }


    /**
     * 根据职位id查询工作列表
     * @param id
     * @return
     */

    List<Work> getWorkListByCategoryId(Integer id);
    /**
     * 根据ids查询职位
     * @param ids
     * @return
     */
    List<Work> selectListByIds(List<Integer> ids);
}

