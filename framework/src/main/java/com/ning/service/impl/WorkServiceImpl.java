package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ning.domain.dto.ResumeCommitDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.*;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;

import com.ning.constants.SystemConstants;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.mapper.*;

import com.ning.service.ClassifyService;
import com.ning.service.RelationService;
import com.ning.service.WorkService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.RedisCache;
import com.ning.utils.SecurityUtils;
import kotlin.jvm.internal.Lambda;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (Resume)表服务实现类
 *
 * @author makejava
 * @since 2024-01-09 23:25:51
 */
@Service
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements WorkService {
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private WorkUserMapper workUserMapper;
    @Autowired
    private ClassifyService classifyService;
    @Autowired
    private ResumeMapper resumeMapper;



    /**
     * 分页条件查询对应职位
     * @param workDto
     * @return
     */
    @Override
    public Result<PageResult> getListByTag(WorkDto workDto) {
        int pageNum = workDto.getPageNum();
        int pageSize = workDto.getPageSize();
        Work work = BeanCopyUtils.copyBean(workDto, Work.class);
        LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
        Page<Work> page = new Page<Work>(pageNum, pageSize);
        List<Work> records = new ArrayList<>();
        if (work != null) {
            wrapper.like(StringUtils.hasText(work.getTitle()), Work::getTitle, work.getTitle())
                    .eq(work.getClassifyId() != null, Work::getClassifyId, work.getClassifyId())
                    .eq(work.getCityName() != null, Work::getCityName, work.getCityName())
                    .eq(work.getEducation() != null, Work::getEducation, work.getEducation());
            page(page, wrapper);
            records = page.getRecords();
        } else {
            records = page.getRecords();
        }

        List<WorkVo> workVos = BeanCopyUtils.copyBeanList(records, WorkVo.class);
        workVos.forEach(item -> {
            Integer WorkId = item.getId();
            Company company = relationMapper.getCompanyByWorkId(WorkId);
            item.setCompanyId(company.getId());
            item.setCompany(company.getBrandName());
        });

        return Result.success(new PageResult(workVos.size(), workVos));

    }

    // 薪资查询
//        if(work!= null && work.getSalary()!= null) {
//            String salary = workDto.getSalary();
//            String[] split = salary.split("-");
//            String start = split[0] + "k";
//            String end = (split[1].replace("[^\\d.]", ""));
//            wrapper.le(Work::getMinSa,end)
//                    .ge(Work::getMaxSa,start);
//        }
    //这里是薪资范围查询
//        if(work!= null && work.getSalary()!= null){
//            String salary = work.getSalary();
//            String[] split = salary.split("-");
//            int start = Integer.parseInt(split[0]);
//            int end = Integer.parseInt(split[1].replace("[^\\d.]", ""));
//
//            records = records.stream().map(item -> {
//                String newSalary = item.getSalary();
//                String[] newSplite = newSalary.split("-");
//                int s = Integer.parseInt(newSplite[0]);
//                int e = Integer.parseInt(newSplite[1].replace("[^\\d.]", ""));
//                if ((s >= start && s <= end) || (e >= start && e <= end)) {
//                    return item;
//                }
//                return null;
//            }).collect(Collectors.toList());
//        }

//    /**
//     * 新增职位接口
//     * @param work
//     * @return
//     */
//    @Override
//    public Result<String> saveByWork(Work work) {
//        work = setMaxMinSa(work);
//        save(work);
//        Integer workId = work.getId();
//        return Result.success();
//    }


    /**
     * 根据id查询职位信息,回显
     *
     * @param id
     * @return
     */
    @Override
    public Result<WorkVo> getByWorkId(Integer id) {
        Work work = workMapper.selectById(id);
        if (work == null){
            throw new BaseException("没有该职位，请检查输入");
        }
        WorkVo workVo = new WorkVo();
        BeanUtils.copyProperties(work, workVo);
        try {
            // 如果用户是已经登录的用户，那么将此次浏览放入历史记录中
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer userId = userDto.getUser().getId();


            LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(History::getWorkId, id).eq(History::getUserId, userId);
            History selectOne = historyMapper.selectOne(wrapper);
            if (selectOne == null) {

                History history = new History();
                history.setWorkId(id).setUserId(userId).setTitle(work.getTitle());
                historyMapper.insert(history);
            }

        } catch (Exception e) {
            //不处理
        }
        //TODO 这里可能因为职位绑定的id是空所以返回公司为空，出现code400异常
        // viewCount可能也有问题，没有更新
        Company company = relationMapper.getCompanyByWorkId(id);
        if(company.getId() == null){
            return Result.error("职位没有绑定的公司");
        }
        workVo.setCompany(company.getBrandName());

        workVo.setCompanyId(company.getId());

        Integer viewCount = (Integer) redisTemplate.opsForHash().get(SystemConstants.WORK_VIEW_COUNT, id.toString());
        workVo.setViewCount(Long.valueOf(viewCount));

        return Result.success(workVo);
    }

    /**
     * 更新职位信息
     *
     * @param workVo
     * @return
     */
    @Override
    @Transactional
    public Result<String> updateByWork(WorkVo workVo) {
        Work work = BeanCopyUtils.copyBean(workVo, Work.class);


        //TODO 分类相关的要重写
//        LambdaQueryWrapper<Classify> wrapper = new LambdaQueryWrapper<>();
//        // 这里搜索所有的分类 后面设置分类的id
//        wrapper.eq(workDto.getBigClassify() != null, Classify::getBigClassify, workDto.getBigClassify())
//                .eq(workDto.getMidClassify() != null, Classify::getMidClassify, workDto.getMidClassify())
//                .eq(workDto.getSmallClassify() != null, Classify::getSmallClassify, workDto.getSmallClassify())
//                .eq(workDto.getSalaryClassify() != null, Classify::getSalaryClassify, workDto.getSalaryClassify());
//        Classify classify = classifyService.getOne(wrapper);
//
//        if (classify == null) {
//            throw new BaseException(SystemConstants.HAS_NO_CATIGORY);
//        }
//        Integer classifyId = classify.getId();

//        //这个是设置了最大和最小的薪资
//        if (workDto.getMinSa() != null && workDto.getMaxSa() != null) {
//            work.setMinSa(workDto.getMinSa())
//                    .setMaxSa(workDto.getMaxSa())
//                    .setSalary(work.getMinSa() + "-" + work.getMaxSa());
//        }

//        LambdaQueryWrapper<Relation> wrapper1 = new LambdaQueryWrapper<>();
//        wrapper1.eq(Relation::getWorkId, workDto.getId());
//        Relation relation = relationMapper.selectOne(wrapper1);
//        Company company = companyMapper.selectById(relation.getCompanyId());
//        //由于职位-分类是多对1的，所以要先删除这个职位对应的分类，再添加分类
//

//        work.setClassifyId(classifyId);

        updateById(work);
        return Result.success();
    }

    /**
     * 批量删除职位
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Result<String> deleteByIds(List<Integer> ids) {
        removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 更新redis中对应的职位浏览量
     *
     * @param id
     * @return
     */
    @Override
    public Result<String> updateViewCount(Long id) {
        try {
            redisTemplate.opsForHash().increment(SystemConstants.WORK_VIEW_COUNT, id.toString(), 1);
        } catch (Exception e) {
            throw new BaseException(e.toString());
        }
        return Result.success();
    }

    /**
     * 根据分类id查询信息
     *
     * @param id
     * @return
     */
    @Override
    public Result<List<WorkVo>> getList(Integer id) {
        List<WorkVo> workList = null;
        try {
            workList = (List<WorkVo>) redisTemplate.opsForHash().get(SystemConstants.WORK_CATIGORY, id.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (workList != null && workList.size() > 0) {
            return Result.success(workList);
        } else {
            // TODO 根据分类查询对应的职位信息
//            List<Work> works = workMapper.getWorkListByCategoryId(id);
            LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Work::getClassifyId,id);
            List<Work> works = workMapper.selectList(wrapper);
            List<WorkVo> workVoList = works.stream().map(item -> {
                Integer workId = item.getId();
                Company company = relationMapper.getCompanyByWorkId(workId);
                WorkVo workVo = BeanCopyUtils.copyBean(item, WorkVo.class);
                workVo.setCompany(company.getBrandName());
                workVo.setCompanyId(company.getId());
                return workVo;
            }).collect(Collectors.toList());

            redisTemplate.opsForHash().put(SystemConstants.WORK_CATIGORY, id.toString(), workVoList);
            return Result.success();
        }
    }

    /**
     * 用户投递简历接口
     *
     * @param resumeCommitDto
     * @return
     */
    @Override
    @Transactional
    public Result<String> commitResume(ResumeCommitDto resumeCommitDto) {
        User user;
        try {
            UserDto loginUser = SecurityUtils.getLoginUser();
            user = loginUser.getUser();
            if (!Objects.equals(user.getId(), resumeCommitDto.getUserId())){
                return Result.error(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
            }
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        Work work = this.getById(resumeCommitDto.getWorkId());
        if (work == null) {
            throw new BaseException(SystemConstants.WORK_NOT_EXIST);
        }
        try {
            //拿到了简历id
            WorkUser workUser = new WorkUser();
            workUser.setUserId(user.getId())
                    .setWorkId(resumeCommitDto.getWorkId())
                    .setResumeId(resumeCommitDto.getResumeId());
            workUserMapper.insert(workUser);
            return Result.success("投递成功");
        } catch (Exception e) {
            throw new BaseException(SystemConstants.USER_HAS_NO_RESUME);
        }
    }
}


// 关于公司如何精准查看某一个用户的简历的实现方法
// TODO 是否要添加，当用户同意公司查看简历时，才对其回显
// 目前实现方法：前端可以添加提示信息，让用户是否同意对方查看简历，如果同意，
// 则运行commitResume接口(简历投递，也就是加入到投递记录中)进行投递
// hr只能从当前所属公司的投递的所有记录汇总中，查找到这个用户id，然后查看其简历


// 如果这么写，后端多表联查数据量大时查询速率会大幅增加
// 那么前端是否要传递当前沟通的职位id以减少查询速率？
// 由于是直接与公司表中的hr进行沟通，传递职位id时是否容易实现？

