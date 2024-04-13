package com.ning.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.annotation.SecurityParameter;
import com.ning.domain.Do.WorkDo;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.dto.ResumeCommitDto;
import com.ning.domain.dto.UserDto;
import com.ning.domain.dto.WorkDto;
import com.ning.domain.entity.*;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.constants.SystemConstants;
import com.ning.domain.vo.WorkPageVo;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.service.*;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @Author: qjn
 * @Date: 2024/1/9 23:26
 */
@RestController
@Slf4j
@Api(tags = "职位相关接口")
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RelationService relationService;
    @Autowired
    private UserCompanyService userCompanyService;

    /**
     * 分页条件查询对应职位
     *
     * @param workDto
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("分页条件查询职位")
    @GetMapping("/page")
    public Result<PageResult> page(WorkDto workDto) {
//        log.info("分页条件查询对应职位:{}", workDto);
        return workService.getListByTag(workDto);
    }


    private User check() {
        Integer isCompany;
        UserDto userDto;
        try {
            userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isCompany = userDto.getUser().getIsCompany();
        } catch (Exception e) {
            throw new BaseException("请先登录后再操作");
        }
        if (!isCompany.equals(SystemConstants.IS_COMPANY)) {
            throw new BaseException("没有该权限");
        }
        return userDto.getUser();
    }



    /**
     * 条件查询公司下发布的职位
     * @param workPageVo
     * @return
     */
    @ApiOperation("条件查询公司下的职位")
    @GetMapping("/WorkListByCompanyId")
    public Result<List<WorkVo>> pageByCategoryId(WorkPageVo workPageVo){
        return companyService.pageUserClientByCategoryId(workPageVo);
    }

    /**
     * 根据id查询职位详细信息,回显
     *
     * @param id
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("id查询职位详细信息")
    @GetMapping("/{id}")
    public Result<WorkVo> getById(@PathVariable Integer id) {
        log.info("查询的职位id：{}", id);
        return workService.getByWorkId(id);
    }



    /**
     * 用户投递简历接口
     *
     * @param resumeCommitDto
     * @return
     */
    //用户投递简历就是添加到历史记录中去
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @GetMapping("/commitResume")
    @ApiOperation("用户投递简历or允许某公司查看自己的简历")
    public Result<String> commitResume(ResumeCommitDto resumeCommitDto) {
        return workService.commitResume(resumeCommitDto);
    }

    /**
     * 判断是否已经投递过此职位
     * @param resumeCommitDto
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("判断是否已经投递过此职位")
    @GetMapping("/judgeCommitted")
    public Result<String> whetherDeliverOrNot(ResumeCommitDto resumeCommitDto){
        return workService.whetherDeliverOrNot(resumeCommitDto);
    }

    /**
     * 更新redis中对应的职位浏览量
     *
     * @param id
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @PutMapping("/updateViewCount/{id}")
    @ApiOperation("更新职位浏览量,用户查看某职位时，同步调用该接口")
    public Result<String> updateViewCount(@PathVariable("id") Long id) {
        log.info("需要更新浏览的职位id是：{}", id);
        return workService.updateViewCount(id);
    }

    /**
     * todo 考虑赛事方（一个用户）如何批量向投入多份简历到职位中
     *
     */


}
