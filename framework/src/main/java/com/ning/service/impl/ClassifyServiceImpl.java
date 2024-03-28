package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.*;
import com.ning.domain.result.Result;
import com.ning.domain.vo.*;
import com.ning.mapper.ClassifyMapper;
import com.ning.mapper.WorkMapper;
import com.ning.service.*;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.RedisCache;
import org.apache.http.util.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Classify)表服务实现类
 *
 * @author makejava
 * @since 2024-03-21 17:02:25
 */
@Service("classifyService")
public class ClassifyServiceImpl extends ServiceImpl<ClassifyMapper, Classify> implements ClassifyService {

    @Autowired
    private ClassifyMapper classifyMapper;
    @Autowired
    private CityClassifyService cityClassifyService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WorkDegreeService workDegreeService;
    @Autowired
    private WorkExperienceService workExperienceService;
    @Autowired
    private WorkSalaryService workSalaryService;
    @Autowired
    private BrandScaleNameService brandScaleNameService;
    /**
     * 查询所有的分类
     * @return
     */
    @Override
    public Result<List<ClassifyShowListVo>> getNormalCategoryList() {
        try {
            //判断redis中是否有此分类
            List<ClassifyShowListVo> catigoryList = (List<ClassifyShowListVo>) redisTemplate.opsForHash().get(SystemConstants.WORK_CATIGORY,SystemConstants.WORK_ALL_LIST);
            if (catigoryList != null && catigoryList.size() > 0){
                return Result.success(catigoryList);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /**
         * redis中没有此分类
         */
        List<String> bigClassifyList = classifyMapper.getBigClassifyList();
        //开始遍历大分类
        List<ClassifyShowListVo> collect = bigClassifyList.stream().map(item -> {
            ClassifyShowListVo classifyShowListVo = new ClassifyShowListVo();

            classifyShowListVo.setClassify(item);

            List<ClassifyShowListVo> midClassifyList = classifyMapper.listByBigClassify(item);

            classifyShowListVo.setChildClassifyList(midClassifyList);

            // 小分类遍历
            midClassifyList.forEach(o -> {
                String classify = o.getClassify();
                List<ClassifyShowListVo> smallClassifyList = classifyMapper.listByMiddleClassify(item,classify);
                o.setChildClassifyList(smallClassifyList);
            });
            return classifyShowListVo;
        }).collect(Collectors.toList());

        redisTemplate.opsForHash().put(SystemConstants.WORK_CATIGORY,SystemConstants.WORK_ALL_LIST,collect);

        return Result.success(collect);
    }

    /**
     * 根据小分类查询职位列表
     * @return
     */
    @Override
    public Result<List<WorkVo>> getWorkListBySmallCategory(Integer CategoryId) {
        try {
            List<WorkVo> workList = (List<WorkVo>) redisTemplate.opsForHash().get(SystemConstants.WORK_CATIGORY,CategoryId.toString());
            if (workList != null && workList.size() > 0){
                return Result.success(workList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Work::getClassifyId,CategoryId);
        List<Work> workList = workMapper.selectList(wrapper);

        List<WorkVo> workVos = BeanCopyUtils.copyBeanList(workList, WorkVo.class);

        redisTemplate.opsForHash().put(SystemConstants.WORK_CATIGORY,CategoryId.toString(),workVos);
        return Result.success(workVos);
    }
    /**
     * 查询城市分类
     * @return
     */
    @Override
    public Result<List<CityClassifyVo>> getAllCities() {

        List<CityClassifyVo> list = (List<CityClassifyVo>) redisTemplate.opsForHash().get(SystemConstants.CITY_CATEGORY,SystemConstants.CATEGORY_LIST);
        assert list != null;
        if (list.size() > 0){
            return Result.success(list);
        }

        List<CityClassify> cities = cityClassifyService.list();
        List<CityClassifyVo> cityClassifyVos = BeanCopyUtils.copyBeanList(cities, CityClassifyVo.class);
        redisTemplate.opsForHash().put(SystemConstants.CITY_CATEGORY,SystemConstants.CATEGORY_LIST,cityClassifyVos);
        return Result.success(cityClassifyVos);
    }

    /**
     * 查询所有学历要求
     * @return
     */
    @Override
    public Result<List<WorkDegreeVo>> getWorkDegreeList() {
        List<WorkDegreeVo> workDegreeVos = (List<WorkDegreeVo>)redisTemplate.opsForHash().get(SystemConstants.WORK_DEGREE,SystemConstants.CATEGORY_LIST);
        if (workDegreeVos != null){
            return Result.success(workDegreeVos);
        }
        List<WorkDegree> workDegrees = workDegreeService.list();
        List<WorkDegreeVo> workDegreeVoList = BeanCopyUtils.copyBeanList(workDegrees, WorkDegreeVo.class);
        redisTemplate.opsForHash().put(SystemConstants.WORK_DEGREE,SystemConstants.CATEGORY_LIST,workDegreeVoList);
        return Result.success(workDegreeVoList);
    }
    /**
     * 公司规模分类
     * @return
     */
    @Override
    public Result<List<BrandScaleNameVo>> getScaleList() {
        List<BrandScaleNameVo> brandScaleNameVos = getHashValue(SystemConstants.SCALE,SystemConstants.CATEGORY_LIST);
        if (brandScaleNameVos != null){
            return Result.success(brandScaleNameVos);
        }
        List<BrandScaleName> brandScaleNames = brandScaleNameService.list();
        brandScaleNameVos = BeanCopyUtils.copyBeanList(brandScaleNames, BrandScaleNameVo.class);

        putHashValue(SystemConstants.SCALE,SystemConstants.CATEGORY_LIST,brandScaleNameVos);

        return Result.success(brandScaleNameVos);
    }

    /**
     * 工作经验分类
     * @return
     */
    @Override
    public Result<List<WorkExperienceVo>> getExpList() {
        List<WorkExperienceVo> workExperienceVos = getHashValue(SystemConstants.WORK_EXP,SystemConstants.CATEGORY_LIST);
        if (workExperienceVos != null){
            return Result.success(workExperienceVos);
        }
        List<WorkExperience> workExperiences = workExperienceService.list();
        workExperienceVos = BeanCopyUtils.copyBeanList(workExperiences, WorkExperienceVo.class);
        putHashValue(SystemConstants.WORK_EXP,SystemConstants.CATEGORY_LIST,workExperienceVos);
        return Result.success(workExperienceVos);
    }
    /**
     * id查询职位分类
     * @param workClazzId
     * @return
     */
    @Override
    public Result<ClassifyVo> getWorkClassifyById(Integer workClazzId) {
        Classify classify = getById(workClazzId);
        return Result.success(BeanCopyUtils.copyBean(classify,ClassifyVo.class));
    }
    /**
     * id查询城市分类
     * @param cityId
     * @return
     */
    @Override
    public Result<CityClassifyVo> getCityById(Integer cityId) {
        CityClassify cityClassify = cityClassifyService.getById(cityId);
        return Result.success(BeanCopyUtils.copyBean(cityClassify,CityClassifyVo.class));
    }
    /**
     * id查询学历
     * @param degreeId
     * @return
     */
    @Override
    public Result<WorkDegreeVo> getDegreeById(Integer degreeId) {
        WorkDegree workDegree = workDegreeService.getById(degreeId);
        return Result.success(BeanCopyUtils.copyBean(workDegree,WorkDegreeVo.class));
    }
    /**
     * id查询规模分类
     * @param scaleId
     * @return
     */
    @Override
    public Result<BrandScaleNameVo> getScaleById(Integer scaleId) {
        BrandScaleName scaleName = brandScaleNameService.getById(scaleId);
        return Result.success(BeanCopyUtils.copyBean(scaleName,BrandScaleNameVo.class));
    }

    /**
     * id查询exp分类
     * @param expId
     * @return
     */
    @Override
    public Result<WorkExperienceVo> getExpById(Integer expId) {
        WorkExperience exp = workExperienceService.getById(expId);
        return Result.success(BeanCopyUtils.copyBean(exp,WorkExperienceVo.class));
    }

    /**
     * 取数据
     * @param key
     * @param hKey
     * @return
     * @param <T>
     */
    private  <T> T getHashValue(Object key, Object hKey){
        HashOperations<Object, Object, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 存数据
     * @param key
     * @param hKey
     * @param value
     * @param <T>
     */
    public <T> void putHashValue(Object key, Object hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }


}

