package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.SimilarPosition;
import com.ning.domain.entity.Work;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;
import com.ning.mapper.SimilarPositionMapper;
import com.ning.service.SimilarPositionService;
import com.ning.service.WorkService;
import com.ning.utils.BeanCopyUtils;
import com.rabbitmq.client.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * (SimilarPosition)表服务实现类
 *
 * @author makejava
 * @since 2024-04-12 19:09:46
 */
@Service("similarPositionService")
public class SimilarPositionServiceImpl extends ServiceImpl<SimilarPositionMapper, SimilarPosition> implements SimilarPositionService {
    @Autowired
    private WorkService workService;
    /**
     * 传入职位id查找相似职位
     * @param workId
     * @return
     */
    @Override
    public Result<List<WorkVo>> getSimilarByWorkId(Integer workId) {
        LambdaQueryWrapper<SimilarPosition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SimilarPosition::getWorkId,workId);
        SimilarPosition similarPosition = getOne(wrapper);
        String similarList = similarPosition.getSimilarId();
        String[] split = similarList.split(",");
        List<WorkVo> workVos = new ArrayList<>();
        for (String s : split) {
            Integer positionId = Integer.valueOf(s);
            Work work = workService.getById(positionId);
            if (work != null) {
                WorkVo workVo = BeanCopyUtils.copyBean(work, WorkVo.class);
                workVos.add(workVo);
            }
        }
        return Result.success(workVos);
    }
}

