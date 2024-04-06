package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.FavorDto;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.Favor;
import com.ning.domain.entity.User;
import com.ning.domain.entity.Work;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.mapper.FavorMapper;
import com.ning.mapper.RelationMapper;
import com.ning.service.FavorService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private RelationMapper relationMapper;
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
        favor = favorMapper.getByFavor(favor);
        if(favor == null){
            throw new BaseException("未收藏该职位!");
        }

        favorMapper.deleteById(favor);
        return Result.success();
    }
    /**
     * 查询该用户的所有收藏
     * @return
     */
    @Override
    public Result<List<WorkVo>> getAllFavors() {
        User user = checkUserLogin();
        List<Work> workList = favorMapper.getAllFavorByUserId(user.getId());
        if (workList.size() < 1){
            throw new BaseException(SystemConstants.USER_HAS_NO_FAVOR);
        }
        List<WorkVo> workVoList = workList.stream().map(item -> {
            Integer workId = item.getId();
            Company company = relationMapper.getCompanyByWorkId(workId);
            WorkVo workVo = BeanCopyUtils.copyBean(item, WorkVo.class);
            workVo.setCompany(company.getBrandName());
            workVo.setCompanyId(company.getId());
            return workVo;
        }).collect(Collectors.toList());
        return Result.success(workVoList);
    }

    private User checkUserLogin(){
        User user = null;
        try {
            user = SecurityUtils.getLoginUser().getUser();
        }catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        return user;
    }
    /**
     * 根据职位id查询该用户是否收藏
     * @param workId
     * @return
     */
    @Override
    public Result<String> getListByWorkId(Integer workId) {
        User user = checkUserLogin();
        Work work = favorMapper.getFavorByUserAndWorkId(user.getId(),workId);
        if (work == null){
            return Result.error(SystemConstants.USER_NOT_FAVOR_THIS_WORK);
        }

        return Result.success("该用户已收藏此职位");
    }


}

