package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ning.domain.dto.CompanyDto;
import com.ning.domain.dto.FollowDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.Follow;
import com.ning.domain.result.Result;
import com.ning.mapper.FollowMapper;
import com.ning.service.CompanyService;
import com.ning.service.FollowService;
import com.ning.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Follow)表服务实现类
 *
 * @author makejava
 * @since 2024-02-24 18:08:38
 */
@Service("followService")
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
    @Autowired
    private CompanyService companyService;
    /**
     * 关注公司
     * @param followDto
     * @return
     */
    @Override
    public Follow insertByDto(FollowDto followDto) {
        Follow follow = BeanCopyUtils.copyBean(followDto, Follow.class);
        save(follow);
        return follow;
    }

    /**
     * 查询是否关注
     * @param followDto
     * @return
     */
    @Override
    public FollowDto getByDto(FollowDto followDto) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId,followDto.getUserId())
                .eq(Follow::getCompanyId,followDto.getCompanyId());
        Follow follow = getOne(wrapper);
        if(follow != null){
            return followDto;
        }
        return null;
    }

    /**
     * 根据用户id和公司id取消关注该公司
     * @param followDto
     * @return
     */
    @Override
    public Result<String> cancelFollow(FollowDto followDto) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId,followDto.getUserId())
                        .eq(Follow::getCompanyId,followDto.getCompanyId());
        Follow follow = getOne(wrapper);
        if(follow == null){
            throw new RuntimeException("错误，您没有关注此公司");
        }
        remove(wrapper);
        return Result.success("取消关注");
    }

    /**
     * 查看当前用户的所有关注列表
     * @return
     */
    @Override
    public List<CompanyDto> getAllCompanyByUserId() {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = userDto.getUser().getId();
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId, userId);
        List<Follow> list = list(wrapper);

        List<CompanyDto> collect = list.stream().map(item -> {
            Company company = companyService.getById(item.getCompanyId());
            return BeanCopyUtils.copyBean(company, CompanyDto.class);
        }).collect(Collectors.toList());
        return collect;
    }
}

