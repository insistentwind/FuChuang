package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.*;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeDrawVo;
import com.ning.domain.vo.ResumeVo;
import com.ning.exception.BaseException;
import com.ning.mapper.*;
import com.ning.service.*;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    @Autowired
    private ResumeDrawMapper resumeDrawMapper;
    @Autowired
    private ResumeMapper resumeMapper;

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
            if (userResume == null) {
                throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }

        if (resumeId == null) {
            throw new BaseException(SystemConstants.PARAMS_MUST_NOT_BE_NULL);
        }
        LambdaQueryWrapper<ResumeDraw> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResumeDraw::getResumeId, resumeId);
        ResumeDraw resumeDraw = getOne(wrapper);
        if (resumeDraw == null) {
            throw new BaseException(SystemConstants.NO_SUCH_RESUME_DRAW);
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
     *
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

    /**
     * 分页查询简历池画像
     *
     * @param pageSize
     * @param offset
     * @return
     */
    @Override
    public List<ResumeDraw> getDrawByPage(Integer pageSize, Integer offset) {
        return resumeDrawMapper.getDrawByPage(pageSize, offset);
    }

    @Autowired
    private UserCompanyMapper userCompanyMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private WorkService workService;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private WorkUserService workUserService;

    /**
     * 根据简历id查询对应画像
     * 管理端
     *
     * @param resumeId
     * @return
     */
    @Override
    public Result<ResumeDrawVo> getDrawByIdWithoutLogin(Integer resumeId) {
        Resume resume = resumeMapper.selectById(resumeId);
        if (resume == null) {
            throw new BaseException("当前简历不存在");
        }
        if (resumeId == null) {
            throw new BaseException(SystemConstants.PARAMS_MUST_NOT_BE_NULL);
        } else if (Objects.equals(resume.getPublicPool(), SystemConstants.PUBLIC_POOL_OPEN)){
            //此时简历放在公共池，可以直接回显
            ResumeDraw resumeDraw = resumeDrawMapper.getDrawByResumeId(resumeId);
            if (resumeDraw == null) {
                throw new BaseException(SystemConstants.CANT_FIND_DRAW);
            }
            return Result.success(BeanCopyUtils.copyBean(resumeDraw, ResumeDrawVo.class));

        }
        /**
         *
         * 没有放在公共池就需要判断可不可以看
         */
        // 拿到了当前操作者所属的公司id
        Integer companyId;
        try {
            Integer hrId = SecurityUtils.getUserId();
            LambdaQueryWrapper<UserCompany> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserCompany::getUserId, hrId);
            UserCompany userCompany = userCompanyMapper.selectOne(wrapper);
            companyId = userCompany.getCompanyId();
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        try {
            // 根据用户找到当前所属公司
            // 通过公司id查询发布的所有职位
            /**
             * SELECT * FROM relation re
             * LEFT JOIN work w ON re.work_id = w.id
             * LEFT JOIN work_user wu ON w.id = wu.work_id
             * WHERE wu.user_id = 12 AND re.company_id = 3;
             */
            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getResumeId, resumeId);
            List<UserResume> list = userResumeService.list(wrapper);
            UserResume userResume = list.get(0);
            Integer userId = userResume.getUserId();
            if (relationMapper.getUserByCompany(companyId, userId) > 0) {
                //说明当前用户已经投递简历，直接回显即可
                ResumeDraw resumeDraw = resumeDrawMapper.getDrawByResumeId(resumeId);
                if (resumeDraw == null) {
                    throw new BaseException(SystemConstants.CANT_FIND_DRAW);
                }
                return Result.success(BeanCopyUtils.copyBean(resumeDraw, ResumeDrawVo.class));
            }
            return Result.error(SystemConstants.USER_NO_PERMITED);
        }
        catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NO_PERMITED);
        }
    }
}

