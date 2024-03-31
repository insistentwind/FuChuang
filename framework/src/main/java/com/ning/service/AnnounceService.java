package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.dto.AnnouncePublishDto;
import com.ning.domain.entity.Announce;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.AnnouncePageVo;
import com.ning.domain.vo.AnnounceVo;

import java.util.List;

/**
 * 文章表(Announce)表服务接口
 *
 * @author makejava
 * @since 2024-03-31 17:49:14
 */
public interface AnnounceService extends IService<Announce> {
    /**
     * 发布系统公告
     * @return
     */
    Result<String> add(AnnouncePublishDto announcePublishDto);

    /**
     * 查看公告列表
     *
     * @return
     */
    Result<PageResult> getPage(AnnouncePageVo announcePageVo);
    /**
     * 公告内容回显
     * @return
     */
    Result<AnnounceVo> selectAnnounceById(Integer id);
    /**
     * 更新公告
     */
    Result<String> updateByEntity(AnnouncePublishDto announcePublishDto);
    /**
     * 逻辑删除公告
     * @param ids
     * @return
     */
    Result<String> deleteById(List<Integer> ids);
}

