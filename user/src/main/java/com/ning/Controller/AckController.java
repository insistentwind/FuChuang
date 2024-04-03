package com.ning.Controller;

import com.ning.constants.SystemConstants;
import com.ning.domain.entity.Ack;
import com.ning.domain.entity.UserPermitcompany;
import com.ning.domain.result.Result;
import com.ning.domain.vo.UserPermitcompanyVo;
import com.ning.service.AckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/04/03 22:31
 **/
@Controller
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
    @ApiOperation("根据id查看简历申请")
    @GetMapping("/application/{id}")
    public Result<Ack> getOneMessage(@PathVariable Integer id){
        Ack byId = ackService.getById(id);
        byId.setRead(SystemConstants.HAS_READ);
        ackService.updateById(byId);
        return Result.success(byId);
    }

    /**
     * 允许公司查看简历
     * @param userPermitcompanyVo
     * @return
     */
    @GetMapping("/allow")
    @ApiOperation("是否同意公司查看简历,1同意,0不同意")
    public Result<String> allowCompanyCheckResume(UserPermitcompanyVo userPermitcompanyVo){
        return ackService.allow(userPermitcompanyVo);
    }
}