package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.dto.FavorDto;
import com.ning.domain.entity.Favor;
import com.ning.domain.entity.Work;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;
import com.ning.mapper.FavorMapper;
import com.ning.service.FavorService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Favor)表服务实现类
 *
 * @author makejava
 * @since 2024-02-01 18:01:18
 */
@Service("favorService")
public class FavorServiceImpl extends ServiceImpl<FavorMapper, Favor> implements FavorService {
    @Autowired
    private FavorMapper favorMapper;
    /**
     * 收藏职位
     * @param favorDto
     * @return
     */
    @Override
    public Result<String> create(FavorDto favorDto) {
        Favor favor = BeanCopyUtils.copyBean(favorDto, Favor.class);
        save(favor);
        return Result.success();
    }

    /**
     * 取消收藏
     * @param favorDto
     * @return
     */
    @Override
    public Result<String> unFavor(FavorDto favorDto) {
        Favor favor = BeanCopyUtils.copyBean(favorDto, Favor.class);
        if(favorMapper.getByFavor(favor) != null){
            throw new RuntimeException("未收藏该职位!");
        }
        removeById(favor);
        return Result.success();
    }
    /**
     * 根据id查询该用户的所有收藏
     * @param id
     * @return
     */
    @Override
    public Result<List<WorkVo>> getAllFavors(Integer id) {
        List<Work> workList = favorMapper.getAllFavorByUserId(id);
        List<WorkVo> workVoList = workList.stream().map(item -> {
            WorkVo workVo = BeanCopyUtils.copyBean(item, WorkVo.class);
            return workVo;
        }).collect(Collectors.toList());
        return Result.success(workVoList);
    }


}

