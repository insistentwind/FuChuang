package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ClassifyShowListVo;
import com.ning.domain.vo.ClassifyVo;
import com.ning.mapper.ClassifyMapper;
import com.ning.domain.entity.Classify;
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
    private RedisTemplate redisTemplate;
    /**
     * 查询所有的分类
     * @return
     */
    @Override
    public Result<List<ClassifyShowListVo>> getNormalCategoryList() {
        try {
            //判断redis中是否有此分类
            List<ClassifyShowListVo> catigoryList = (List<ClassifyShowListVo>) redisTemplate.opsForValue().get(SystemConstants.WORK_CATIGORY);
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


            midClassifyList.forEach(o -> {
                String classify = o.getClassify();
                List<ClassifyShowListVo> smallClassifyList = classifyMapper.listByMiddleClassify(item,classify);
                o.setChildClassifyList(smallClassifyList);
            });
            return classifyShowListVo;
        }).collect(Collectors.toList());

        redisTemplate.opsForValue().set(SystemConstants.WORK_CATIGORY,collect);

        return Result.success(collect);
    }
}

