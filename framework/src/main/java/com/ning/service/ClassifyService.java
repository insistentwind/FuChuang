package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Classify;
import com.ning.domain.entity.Work;
import com.ning.domain.entity.WorkDegree;
import com.ning.domain.entity.WorkExperience;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;

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
    /**
     * 根据小分类查询职位列表
     * @return
     */
    Result<List<WorkVo>> getWorkListBySmallCategory(Integer CategoryId);
    /**
     * 查询城市分类
     * @return
     */
    Result<List<CityClassifyVo>> getAllCities();
    /**
     * 查询所有学历要求
     * @return
     */
    Result<List<WorkDegreeVo>> getWorkDegreeList();
    /**
     * 公司规模分类
     * @return
     */
    Result<List<BrandScaleNameVo>> getScaleList();

    /**
     * 工作经验分类
     * @return
     */
    Result<List<WorkExperienceVo>> getExpList();
    /**
     * id查询职位分类
     * @param id
     * @return
     */
    Result<ClassifyVo> getWorkClassifyById(Integer id);

    /**
     * id查询城市分类
     * @param cityId
     * @return
     */
    Result<CityClassifyVo> getCityById(Integer cityId);
    /**
     * id查询学历
     * @param degreeId
     * @return
     */
    Result<WorkDegreeVo> getDegreeById(Integer degreeId);
    /**
     * id查询规模分类
     * @param scaleId
     * @return
     */
    Result<BrandScaleNameVo> getScaleById(Integer scaleId);
    /**
     * id查询exp分类
     * @param expId
     * @return
     */
    Result<WorkExperienceVo> getExpById(Integer expId);
}

