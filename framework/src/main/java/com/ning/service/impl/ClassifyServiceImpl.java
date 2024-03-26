package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.CityClassify;
import com.ning.domain.entity.Work;
import com.ning.domain.result.Result;
import com.ning.domain.vo.CityClassifyVo;
import com.ning.domain.vo.ClassifyShowListVo;
import com.ning.domain.vo.ClassifyVo;
import com.ning.domain.vo.WorkVo;
import com.ning.mapper.ClassifyMapper;
import com.ning.domain.entity.Classify;
import com.ning.mapper.WorkMapper;
import com.ning.service.CityClassifyService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ning.service.ClassifyService;

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
    private WorkMapper workMapper;
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

        List<CityClassifyVo> list = (List<CityClassifyVo>) redisTemplate.opsForValue().get(SystemConstants.CITY_CATEGORY);
        assert list != null;
        if (list.size() > 0){
            return Result.success(list);
        }

        List<CityClassify> cities = cityClassifyService.list();
        List<CityClassifyVo> cityClassifyVos = BeanCopyUtils.copyBeanList(cities, CityClassifyVo.class);
        redisTemplate.opsForValue().set(SystemConstants.CITY_CATEGORY,cityClassifyVos);
        return Result.success(cityClassifyVos);
    }


}

