package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Ack;
import com.ning.domain.entity.UserPermitcompany;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserPermitcompanyVo;

import java.util.List;

/**
 * (Ack)表服务接口
 *
 * @author makejava
 * @since 2024-04-03 22:16:26
 */
public interface AckService extends IService<Ack> {
    /**
     * 查看所有收到的查看简历申请
     * @return
     */
    Result<List<Ack>> getAll();
    /**
     * 允许公司查看简历
     * @param userPermitcompanyVo
     * @return
     */
    Result<String> allow(UserPermitcompanyVo userPermitcompanyVo);

    /**
     * 查看所有收到的简历查看申请
     * @return
     */
    Result<List<Ack>> getCompanyAll();
}

