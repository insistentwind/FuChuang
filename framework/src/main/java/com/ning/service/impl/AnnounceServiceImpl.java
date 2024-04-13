package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.AnnouncePublishDto;
import com.ning.domain.entity.User;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.AnnouncePageVo;
import com.ning.domain.vo.AnnounceVo;
import com.ning.exception.BaseException;
import com.ning.mapper.AnnounceMapper;
import com.ning.domain.entity.Announce;
import com.ning.service.AnnounceService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 文章表(Announce)表服务实现类
 *
 * @author makejava
 * @since 2024-03-31 17:49:14
 */
@Service("announceService")
public class AnnounceServiceImpl extends ServiceImpl<AnnounceMapper, Announce> implements AnnounceService {

    /**
     * 发布系统公告
     * @return
     */
    @Override
    public Result<String> add(AnnouncePublishDto announcePublishDto) {
        User user = null;
        try {
            user = SecurityUtils.getLoginUser().getUser();
            if (!Objects.equals(user.getIsCompany(), SystemConstants.IS_ADMIN)){
                return Result.error(SystemConstants.IS_NOT_ADMIN + "或没有权限");
            }
        }catch (Exception e){
            throw new BaseException("请先登录再重试");
        }

        //添加系统公告
        Announce announce = BeanCopyUtils.copyBean(announcePublishDto, Announce.class);
        save(announce);
        return Result.success();
    }

    /**
     * 查看公告列表
     *
     * @return
     */
    @Override
    public Result<PageResult> getPage(AnnouncePageVo announcePageVo) {
        Page page = new Page(announcePageVo.getPageNum(), announcePageVo.getPageSize());
        LambdaQueryWrapper<Announce> wrapper= new LambdaQueryWrapper<>();

        wrapper.like(announcePageVo.getTitle() != null,Announce::getTitle,announcePageVo.getTitle());
        wrapper.eq(Announce::getStatus,SystemConstants.STATUS_NORMAL);
        wrapper.like(announcePageVo.getSummary() != null,Announce::getSummary,announcePageVo.getSummary());
        page(page,wrapper);

        return Result.success(new PageResult((int) page.getTotal(),page.getRecords()));
    }
    /**
     * 公告内容回显
     * @return
     */
    @Override
    public Result<AnnounceVo> selectAnnounceById(Integer id) {
        Announce announce = getById(id);
        if (announce != null){
            AnnounceVo announceVo = BeanCopyUtils.copyBean(announce, AnnounceVo.class);

            announce.setViewCount(announce.getViewCount() + 1);
            save(announce);
            return Result.success(announceVo);
        }
        return Result.error("没有此公告");
    }
    /**
     * 更新公告
     */
    @Override
    public Result<String> updateByEntity(AnnouncePublishDto announcePublishDto) {
        try {
            Announce announce = BeanCopyUtils.copyBean(announcePublishDto, Announce.class);
            updateById(announce);
            return Result.success();
        }
        catch (Exception e){
            throw new BaseException("发生未知错误");
        }
    }
    /**
     * 逻辑删除公告
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Result<String> deleteById(List<Integer> ids) {
        removeBatchByIds(ids);
        return Result.success();
    }
}

