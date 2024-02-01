package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.dto.FavorDto;
import com.ning.domain.entity.Favor;
import com.ning.domain.entity.Work;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;

import java.util.List;


/**
 * (Favor)表服务接口
 *
 * @author makejava
 * @since 2024-02-01 18:01:18
 */
public interface FavorService extends IService<Favor> {
    /**
     * 收藏职位
     * @param favorDto
     * @return
     */
    Result<String> create(FavorDto favorDto);
    /**
     * 取消收藏
     * @param favorDto
     * @return
     */
    Result<String> unFavor(FavorDto favorDto);
    /**
     * 根据id查询该用户的所有收藏
     * @param id
     * @return
     */
    Result<List<WorkVo>> getAllFavors(Integer id);
}

