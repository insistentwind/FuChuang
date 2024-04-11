package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.result.Result;
import com.ning.domain.vo.DrawVo;
import com.ning.exception.BaseException;
import com.ning.mapper.DrawMapper;
import com.ning.domain.entity.Draw;
import com.ning.service.DrawService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职位画像(Drwa)表服务实现类
 *
 * @author makejava
 * @since 2024-04-09 18:23:36
 */
@Service("drawService")
public class DrawServiceImpl extends ServiceImpl<DrawMapper, Draw> implements DrawService {
    /**
     * 根据职位id找职位画像
     * @param workId
     * @return
     */
    @Override
    public Result<DrawVo> getDrawVoByWorkId(Integer workId) {
        LambdaQueryWrapper<Draw> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Draw::getWorkId,workId);
        Draw draw = getOne(wrapper);
        if (draw == null){
            throw new BaseException(SystemConstants.CANT_FIND_DRAW);
        }
        DrawVo drawVo = BeanCopyUtils.copyBean(draw, DrawVo.class);
        return Result.success(drawVo);
    }
    /**
     * 新增职位画像
     * @param draw
     * @return
     */
    @Override
    public Result<String> insertByEntity(Draw draw) {
        save(draw);
        return Result.success(SystemConstants.SUCCESS);
    }
    /**
     * 修改职位画像
     * @param draw
     * @return
     */
    @Override
    public Result<String> updateByEntity(Draw draw) {
        updateById(draw);
        return Result.success(SystemConstants.SUCCESS);
    }
}

