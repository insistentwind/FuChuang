package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mysql.cj.util.StringUtils;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.Work;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;

import com.ning.domain.vo.WorkVo;
import com.ning.mapper.WorkMapper;

import com.ning.service.WorkService;
import com.ning.utils.BeanCopyUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        if( work!= null){
            wrapper.like(work.getDescription() != null,Work::getDescription,work.getDescription())
                    .like(work.getCompany() != null,Work::getCompany,work.getCompany())
                    .eq(work.getEducation() != null,Work::getEducation,work.getEducation());
            wrapper.orderByDesc(Work::getSalary);
        }
        //薪资查询
        if(work!= null && work.getSalary()!= null) {
            String salary = workDto.getSalary();
            String[] split = salary.split("-");
            String start = split[0] + "k";
            String end = (split[1].replace("[^\\d.]", ""));
            wrapper.le(Work::getMinSa,end)
                    .ge(Work::getMaxSa,start);
        }

        Page<Work> page = new Page<Work>(pageNum,pageSize);
        page(page,wrapper);
        List<Work> records = page.getRecords();

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

        return Result.success(new PageResult(records.size(),records));
    }
    /**
     * 新增职位接口
     * @param work
     * @return
     */
    @Override
    public Result<String> saveByWork(Work work) {
        work = setMaxMinSa(work);
        save(work);
        return Result.success();
    }

    private Work setMaxMinSa(Work work){
        String salary = work.getSalary();
        String[] split = salary.split("-");
        work.setMinSa(split[0] + "k");
        work.setMaxSa(split[1].replace("[^\\d.]", ""));
        return work;
    }

    /**
     * 根据id查询职位,回显
     * @param id
     * @return
     */
    @Override
    public Result<WorkVo> getByWorkId(Integer id) {
        Work work = getById(id);
        WorkVo workVo = new WorkVo();
        BeanUtils.copyProperties(work,workVo);
        return Result.success(workVo);
    }
    /**
     * 更新职位信息
     * @param workDto
     * @return
     */
    @Override
    public Result<String> updateByWork(WorkDto workDto) {
        Work work = BeanCopyUtils.copyBean(workDto, Work.class);
        work = setMaxMinSa(work);
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
}

