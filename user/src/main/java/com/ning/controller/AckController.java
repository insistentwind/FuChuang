package com.ning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.annotation.SecurityParameter;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.Ack;
import com.ning.domain.entity.User;
import com.ning.domain.entity.UserCompany;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserPermitcompanyVo;
import com.ning.exception.BaseException;
import com.ning.service.AckService;
import com.ning.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author: qjn
 * @create: 2024/04/03 22:31
 **/
@RestController
@Slf4j
@RequestMapping("/ack")
@Api(tags = "用户简历消息")
public class AckController {

    @Autowired
    private AckService ackService;
    /**
     * 查看所有收到的查看简历申请
     * @return
     */
    @SecurityParameter(outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("查看所有收到的查看简历申请")
    @GetMapping("/allApplications")
    public Result<List<Ack>> getCompanyMessage(){
        return ackService.getAll();
    }

    /**
     * 根据id查看简历申请
     * @param id
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("根据id查看简历申请")
    @GetMapping("/application/{id}")
    public Result<Ack> getOneMessage(@PathVariable Integer id){
        Ack byId = ackService.getById(id);
        if (byId == null){
            throw new BaseException(SystemConstants.CHECK_INPUT);
        }
        //判断当前用户的信箱里有没有这条消息
        try {
            User user = SecurityUtils.getLoginUser().getUser();
            if (!Objects.equals(byId.getUserId(), user.getId()) || Objects.equals(byId.getIsCompany(), SystemConstants.IS_NOT_COMPANY)){
                return Result.error(SystemConstants.USER_HAS_NO_MSG);
            }
        }
        catch (Exception e){
            throw new BaseException(SystemConstants.USER_NOT_LOGIN_OR_ERROR);
        }
        byId.setIsRead(SystemConstants.HAS_READ);
        ackService.updateById(byId);
        return Result.success(byId);
    }

    /**
     * 允许公司查看简历
     * @param userPermitcompanyVo
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @GetMapping("/allow")
    @ApiOperation("是否同意公司查看简历,1同意,0不同意")
    public Result<String> allowCompanyCheckResume(UserPermitcompanyVo userPermitcompanyVo){
        return ackService.allow(userPermitcompanyVo);
    }
}