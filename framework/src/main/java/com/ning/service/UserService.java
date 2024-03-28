package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.vo.ResumeVo;
import com.ning.domain.entity.User;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.DeliverVo;
import com.ning.domain.vo.UserPageVo;
import com.ning.domain.vo.UserRoleVo;
import com.ning.domain.vo.UserVo;

import java.util.List;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-01-16 12:09:23
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录接口
     * @param user
     * @return
     */
    Result<UserVo> login(User user);
    /**
     * 用户注册
     * @param user
     * @return
     */
    Result<String> register(User user);

    /**
     * 获取当前用户默认的简历信息
     * @return
     */
    Result<ResumeVo> getReusme();
    /**
     * 修改当前用户的信息
     * @param user
     * @return
     */
    Result<String> updateByUser(UserVo user);
    /**
     * 用户注销
     * @return
     */
    Result<String> logout();
    /**
     * 分页条件查询用户信息
     *
     * @param userPageVo
     * @return
     */
    Result<PageResult> pageByUserPageVo(UserPageVo userPageVo);
    /**
     * 新增用户
     * @return
     */
    Result<String> insertByUserRoleVo(UserRoleVo userRoleVo);
    /**
     * 删除固定的某个用户（逻辑删除）
     * @param id
     * @return
     */
    Result<String> deleteById(List<Long> id);
    /**
     * 用户信息回显
     * @return
     */
    Result<UserVo> getInfo();
    /**
     * 查询当前用户的投递历史
     * @return
     */
    Result<List<DeliverVo>> getDliverHistory();
    /**
     * 插入用户简历数据
     * @param resumeVo
     * @return
     */
    Result<String> insertResume(ResumeVo resumeVo);
    /**
     * 简历数据修改
     * @param resumeVo
     * @return
     */
    Result<String> resumeModify(ResumeVo resumeVo);
    /**
     * 设置为默认简历
     * @param resumeId
     * @return
     */
    Result<String> setDefaultResume(Integer resumeId);
    /**
     * 当前用户所创建的简历列表
     * @return
     */
    Result<List<ResumeVo>> getResumeList();
    /**
     * 批量创建简历
     * @param resumeVos
     * @return
     */
    Result<String> deliverBatchResumes(List<ResumeVo> resumeVos);
}

