package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ning.domain.dto.UserDto;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.History;
import com.ning.domain.entity.Relation;
import com.ning.domain.entity.Work;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;

import com.ning.domain.systemConstants.SystemConstants;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.mapper.CompanyMapper;
import com.ning.mapper.HistoryMapper;
import com.ning.mapper.RelationMapper;
import com.ning.mapper.WorkMapper;

import com.ning.service.RelationService;
import com.ning.service.WorkService;
import com.ning.utils.BeanCopyUtils;
import com.ning.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    private static String KEY = "work_category";
    /**
     * 分页条件查询对应简历内容
     * @param workDto
     * @return
     */
    @Override
    public Result<PageResult> getListByTag(WorkDto workDto) {
        int pageNum = workDto.getPageNum();
        int pageSize = workDto.getPageSize();
        Work work = BeanCopyUtils.copyBean(workDto, Work.class);
        LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
        Page<Work> page = new Page<Work>(pageNum,pageSize);
        List<Work> records = new ArrayList<>();
        if( work!= null){
            wrapper.like(StringUtils.hasText(work.getTitle()),Work::getTitle,work.getTitle())
                    .like(StringUtils.hasText(work.getAddress()),Work::getAddress,work.getAddress())
                    .eq(StringUtils.hasText(work.getEducation()),Work::getEducation,work.getEducation())
                    .le(StringUtils.hasText(work.getMaxSa()),Work::getMaxSa,work.getMaxSa())
                    .ge(StringUtils.hasText(work.getMinSa()),Work::getMinSa,work.getMinSa());
//            wrapper.orderByDesc(Work::getSalary);
            page(page,wrapper);
            records = page.getRecords();
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
        else{
            records = page.getRecords();
        }

        System.out.println("total的数量:" + page.getTotal());
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

        return Result.success(new PageResult(Integer.valueOf(records.size()),records));
    }
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

    private Work setMaxMinSa(Work work){
        String salary = work.getSalary();
        String[] split = salary.split("-");
        work.setMinSa(split[0] + "k");
        work.setMaxSa(split[1].replace("[^\\d.]", ""));
        return work;
    }

    /**
     * 根据id查询职位信息,回显
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Result<WorkVo> getByWorkId(Integer id) {
        Work work = getById(id);
        WorkVo workVo = new WorkVo();
        BeanUtils.copyProperties(work,workVo);
        try {
            // 如果用户是已经登录的用户，那么将此次浏览放入历史记录中
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer userId = userDto.getUser().getId();


            LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(History::getWorkId,id).eq(History::getUserId,userId);
            History selectOne = historyMapper.selectOne(wrapper);
            if(selectOne == null){

                History history = new History();
                history.setWorkId(id).setUserId(userId).setTitle(work.getTitle());
                historyMapper.insert(history);
            }

        }
        catch (Exception e){
            //不处理
        }

        Integer viewCount = (Integer) redisTemplate.opsForHash().get(SystemConstants.WORK_VIEW_COUNT,id.toString());
        workVo.setViewCount(Long.valueOf(viewCount));

        return Result.success(workVo);
    }
    /**
     * 更新职位信息
     * @param workDto
     * @return
     */
    @Override
    @Transactional
    public Result<String> updateByWork(WorkDto workDto) {
        Work work = BeanCopyUtils.copyBean(workDto, Work.class);
        //这个是设置了最大和最小的薪资
        if(workDto.getMinSa() != null&& workDto.getMaxSa() != null){
            work.setMinSa(workDto.getMinSa())
                    .setMaxSa(workDto.getMaxSa())
                    .setSalary(work.getMinSa()+"-"+work.getMaxSa());
        }

        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Relation::getWorkId,workDto.getId());
        Relation relation = relationMapper.selectOne(wrapper);
        Company company = companyMapper.selectById(relation.getCompanyId());
        work.setCompany(company.getCompanyName());

        updateById(work);
        return Result.success();
    }
    /**
     * 批量删除职位
     * @param ids
     * @return
     */
    @Override
    public Result<String> deleteByIds(List<Integer> ids) {
        removeBatchByIds(ids);
        return Result.success();
    }
    /**
     * 更新redis中对应的职位浏览量
     * @param id
     * @return
     */
    @Override
    public Result<String> updateViewCount(Long id) {
        try {
            redisTemplate.opsForHash().increment(SystemConstants.WORK_VIEW_COUNT,id.toString(),1);
        }
        catch (Exception e){
            throw new BaseException(e.toString());
        }
        return Result.success();
    }
    /**
     * 根据分类id查询信息
     * @param id
     * @return
     */
    @Override
    public Result<List<WorkVo>> getList(Long id) {
        List<WorkVo> workList= (List<WorkVo>)redisTemplate.opsForHash().get(KEY, id);
        if(workList != null && workList.size()>0){
            return Result.success(workList);
        }
        else{
            // TODO 根据分类查询对应的职位信息
            redisTemplate.opsForHash().put(KEY,id,null);
            return Result.success();
        }
    }
}

