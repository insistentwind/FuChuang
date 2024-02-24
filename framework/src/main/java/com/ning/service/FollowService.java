package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.dto.FollowDto;
import com.ning.domain.entity.Follow;
import com.ning.domain.result.Result;

import java.util.List;

/**
 * (Follow)表服务接口
 *
 * @author makejava
 * @since 2024-02-24 18:08:37
 */
public interface FollowService extends IService<Follow> {
    /**
     * 关注公司
     * @param followDto
     * @return
     */
    Follow insertByDto(FollowDto followDto);

    /**
     * 查询是否关注
     * @param followDto
     * @return
     */
    FollowDto getByDto(FollowDto followDto);
    /**
     * 根据用户id和公司id取消关注该公司
     * @param followDto
     * @return
     */
    Result<String> cancelFollow(FollowDto followDto);
    /**
     * 查看当前用户的所有关注列表
     * @return
     */
    List<CompanyDto> getAllCompanyByUserId();
}

