package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.ResumePageDto;
import com.ning.domain.entity.Resume;
import com.ning.domain.entity.ResumeDraw;
import com.ning.domain.entity.UserResume;
import com.ning.domain.entity.WorkUser;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeVo;
import com.ning.exception.BaseException;
import com.ning.mapper.ResumeDrawMapper;
import com.ning.mapper.ResumeMapper;
import com.ning.mapper.UserResumeMapper;
import com.ning.mapper.WorkUserMapper;
import com.ning.service.ResumeDrawService;
import com.ning.service.ResumeService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.GetResumeInfoUtils;
import com.qiniu.sms.model.TemplateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Resume)表服务实现类
 *
 * @author makejava
 * @since 2024-01-24 23:48:37
 */
@Service("resumeService")
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {
    @Autowired
    private UserResumeMapper userResumeMapper;
    @Autowired
    private GetResumeInfoUtils getResumeInfoUtils;
    @Autowired
    private ResumeDrawService resumeDrawService;
    @Autowired
    private ResumeDrawMapper resumeDrawMapper;
    @Autowired
    private WorkUserMapper workUserMapper;
    /**
     * 查询公共简历池数据
     * @param resumePageDto
     * @return
     */
    @Override
    public Result<List<ResumeVo>> getPage(ResumePageDto resumePageDto) {
        if (resumePageDto.getPageNum() == null || resumePageDto.getPageSize() == null){
            throw new BaseException(SystemConstants.CHECK_INPUT);
        }
        Page<Resume> page = new Page<>(resumePageDto.getPageNum(), resumePageDto.getPageSize(),false);
        LambdaQueryWrapper<Resume> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resume::getPublicPool,SystemConstants.PUBLIC_POOL_OPEN);
        page(page,wrapper);
        List<Resume> records = page.getRecords();
        if (records.size() < 1){
            throw new BaseException(SystemConstants.RESUME_POOL_HAS_NO_RESUME);
        }
        List<ResumeVo> resumeVos = BeanCopyUtils.copyBeanList(records, ResumeVo.class);

        return Result.success(resumeVos);
    }
    /**
     * 设置简历是否放入公共池
     * @param id
     * @return
     */
    @Override
    public Result<String> setNoPool(Integer id) {
        Resume resume1= new Resume();

        Resume resume = getById(id);
        if (resume == null){
            throw new BaseException(SystemConstants.RESUME_POOL_HAS_NO_RESUME);
        }
        Integer publicPool = resume.getPublicPool();
        if (Objects.equals(publicPool, SystemConstants.PUBLIC_POOL_CLOSE)){
            resume1.setPublicPool(SystemConstants.PUBLIC_POOL_OPEN);
        }
        else {
            resume1.setPublicPool(SystemConstants.PUBLIC_POOL_CLOSE);
        }
        resume1.setId(resume.getId());
        updateById(resume1);
        return Result.success(SystemConstants.SUCCESS);
    }
    /**
     * 根据id查询简历数据
     * @param resumeId
     * @return
     */
    @Override
    public Result<ResumeVo> getResumeVoByResumeId(Integer resumeId) {
        Resume resume = getById(resumeId);
        if (!Objects.equals(resume.getPublicPool(), SystemConstants.PUBLIC_POOL_OPEN)){
            throw new BaseException(SystemConstants.HAS_NO_POOL_PERMS);
        }
        else if (Objects.equals(resume.getObscure(), SystemConstants.CAN_BE_SEEN)){
            //这里要查询密钥,进行简历解密
            LambdaQueryWrapper<UserResume> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserResume::getResumeId,resume.getId());
            UserResume userResume = userResumeMapper.selectOne(wrapper);
            try {
                resume = getResumeInfoUtils.getResumeVoByKey(userResume.getUserId(), resume);
                if (resume == null){
                    throw new BaseException(SystemConstants.RESUME_POOL_HAS_NO_RESUME);
                }
            }
            catch (Exception e){
                throw new BaseException(SystemConstants.DECODE_FAIL);
            }
        }
        ResumeVo resumeVo = BeanCopyUtils.copyBean(resume, ResumeVo.class);

        return Result.success(resumeVo);
    }
    /**
     * 简历池画像列表
     * @param resumePageDto
     * @return
     */
    @Override
    public Result<List<ResumeDraw>> getDrawPage(ResumePageDto resumePageDto) {
        if (resumePageDto.getPageNum() == null || resumePageDto.getPageSize() == null){
            throw new BaseException(SystemConstants.CHECK_INPUT);
        }
        Integer pageSize = resumePageDto.getPageSize();
        Integer pageNum = resumePageDto.getPageNum();

        Integer offset = (pageNum-1) * pageSize;

        List<ResumeDraw> drawList = resumeDrawService.getDrawByPage(pageSize,offset);

        if (drawList.size() < 1){
            throw new BaseException(SystemConstants.RESUME_POOL_HAS_NO_RESUME);
        }

        return Result.success(drawList);
    }
    /**
     * 查看职位下简历画像
     * @param workId
     * @return
     */
    @Override
    public Result<List<ResumeDraw>> getDrawByPositionId(Integer workId) {
        LambdaQueryWrapper<WorkUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkUser::getWorkId,workId);
        List<WorkUser> workUsers = workUserMapper.selectList(wrapper);
        List<Integer> resumeIds = workUsers.stream().map(WorkUser::getResumeId).distinct().collect(Collectors.toList());
        List<ResumeDraw> collect = resumeIds.stream()
                .map(item -> resumeDrawMapper.getDrawByResumeId(item)).collect(Collectors.toList());
        return Result.success(collect);
    }
}

