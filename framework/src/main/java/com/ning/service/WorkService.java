package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.dto.ResumeCommitDto;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.Work;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.WorkVo;

import java.util.List;


/**
 * (Resume)表服务接口
 *
 * @author makejava
 * @since 2024-01-09 23:25:49
 */
public interface WorkService extends IService<Work> {
    /**
     * 分页条件查询对应职位
     * @param workDto
     * @return
     */
    Result<PageResult> getListByTag(WorkDto workDto);
//    /**
//     * 新增职位接口
//     * @param work
//     * @return
//     */
//    Result<String> saveByWork(Work work);

    /**
     * 根据id查询职位,回显
     * @param id
     * @return
     */
    Result<WorkVo> getByWorkId(Integer id);
    /**
     * 更新职位信息
     * @param workVo
     * @return
     */
    Result<String> updateByWork(WorkVo workVo);
    /**
     * 批量删除职位
     * @param ids
     * @return
     */
    Result<String> deleteByIds(List<Integer> ids);
    /**
     * 更新redis中对应的职位浏览量
     * @param id
     * @return
     */
    Result<String> updateViewCount(Long id);
    /**
     * 根据分类id查询信息
     * @param id
     * @return
     */
    Result<List<WorkVo>> getList(Integer id);
    /**
     * 用户投递简历接口
     * @param resumeCommitDto
     * @return
     */
    Result<String> commitResume(ResumeCommitDto resumeCommitDto);
    /**
     * 根据ids查询职位
     * @param ids
     * @return
     */
    Result<List<Work>> getWorksByIds(List<Integer> ids);
    /**
     * 判断是否已经投递过此职位
     * @param resumeCommitDto
     * @return
     */
    Result<String> whetherDeliverOrNot(ResumeCommitDto resumeCommitDto);
}

