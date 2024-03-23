package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Classify;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ClassifyShowListVo;
import com.ning.domain.vo.ClassifyVo;

import java.util.List;

/**
 * (Classify)表服务接口
 *
 * @author makejava
 * @since 2024-03-21 17:02:24
 */
public interface ClassifyService extends IService<Classify> {
    /**
     * 查询所有的分类
     * @return
     */
    Result<List<ClassifyShowListVo>> getNormalCategoryList();
}

