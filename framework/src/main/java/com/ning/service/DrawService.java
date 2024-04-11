package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Draw;
import com.ning.domain.result.Result;
import com.ning.domain.vo.DrawVo;

/**
 * 职位画像(Drwa)表服务接口
 *
 * @author makejava
 * @since 2024-04-09 18:23:35
 */
public interface DrawService extends IService<Draw> {
    /**
     * 根据职位id找职位画像
     * @param id
     * @return
     */
    Result<DrawVo> getDrawVoByWorkId(Integer id);
    /**
     * 新增职位画像
     * @param draw
     * @return
     */
    Result<String> insertByEntity(Draw draw);
    /**
     * 修改职位画像
     * @param draw
     * @return
     */
    Result<String> updateByEntity(Draw draw);
}

