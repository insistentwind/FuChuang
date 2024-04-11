package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.User;
import com.ning.domain.entity.UserResume;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeDrawVo;
import com.ning.exception.BaseException;
import com.ning.mapper.ResumeDrawMapper;
import com.ning.domain.entity.ResumeDraw;
import com.ning.service.ResumeDrawService;
import com.ning.service.UserResumeService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简历画像(ResumeDraw)表服务实现类
 *
 * @author makejava
 * @since 2024-04-11 17:09:40
 */
@Service("resumeDrawService")
public class ResumeDrawServiceImpl extends ServiceImpl<ResumeDrawMapper, ResumeDraw> implements ResumeDrawService {

    @Autowired
    private UserResumeService userResumeService;

    /**
     * 根据简历id查询对应画像
     *
     * @param resumeId
     * @return
     */
    @Override
    public Result<ResumeDrawVo> getDrawById(Integer resumeId) {
        try {
            User user = SecurityUtils.getLoginUser().getUser();
            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getUserId, user.getId())
                    .eq(UserResume::getResumeId, resumeId);
            UserResume userResume = userResumeService.getOne(wrapper);
            if (userResume == null){
                throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
            }
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }

        if (resumeId == null) {
            throw new BaseException(SystemConstants.PARAMS_MUST_NOT_BE_NULL);
        }
        LambdaQueryWrapper<ResumeDraw> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResumeDraw::getResumeId, resumeId);
        ResumeDraw resumeDraw = getOne(wrapper);
        if (resumeDraw == null) {
            throw new BaseException(SystemConstants.NO_SUCH_RESUME);
        }
        ResumeDrawVo resumeDrawVo = BeanCopyUtils.copyBean(resumeDraw, ResumeDrawVo.class);
        return Result.success(resumeDrawVo);
    }

    /**
     * 插入对应简历画像
     *
     * @param resumeDrawVo
     * @return
     */
    @Override
    public Result<String> insert(ResumeDrawVo resumeDrawVo) {
        //查询这个简历是不是已经有了对应的简历画像
        Integer resumeId = resumeDrawVo.getResumeId();
        if (resumeId == null) {
            throw new BaseException(SystemConstants.RESUMEDRAW_MUST_HAVE_RESUMEID);
        }
        LambdaQueryWrapper<ResumeDraw> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResumeDraw::getResumeId, resumeId);
        ResumeDraw resumeDraw = getOne(wrapper);

        ResumeDraw newDraw = BeanCopyUtils.copyBean(resumeDrawVo, ResumeDraw.class);
        if (resumeDraw == null) {
            save(newDraw);
        } else {
            updateById(newDraw);
        }
        return Result.success(SystemConstants.SUCCESS);
    }

    /**
     * 删除画像
     * @param resumeId
     * @return
     */
    @Override
    public Result<String> delete(Integer resumeId) {
        boolean value = removeById(resumeId);
        if (value) {
            return Result.success(SystemConstants.SUCCESS);
        } else {
            return Result.error("发生未知错误");
        }
    }
}

