package com.ning.controller;

import com.ning.constants.SystemConstants;
import com.ning.domain.Do.CompanyDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.Company;
import com.ning.domain.entity.User;
import com.ning.domain.entity.UserCompany;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.CompanyVo;
import com.ning.mapper.CompanyMapper;
import com.ning.service.CompanyService;
import com.ning.service.UserCompanyService;
import com.ning.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: qjn
 * @create: 2024/04/11 21:47
 **/
@RestController
@Slf4j
@Api(tags = "审核公司用户")
@RequestMapping("system/check")
public class CompanyCheckController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserCompanyService userCompanyService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 审核公司是否通过
     * @param companyId
     * @param status
     * @return
     */
    @PutMapping("/status")
    @ApiOperation("审核公司是否通过")
    @PreAuthorize(value = "ps.hasPermission(T(com.ning.constants.SystemConstants).SYSTEM_DEPT_ADD)")
    public Result<String> companyCheck(@RequestParam(value = "id")Integer companyId,@RequestParam(value = "status")Integer status){
        if (Objects.equals(status, SystemConstants.COMPANY_CHECK_PASS)) {
            Company company = Company.builder()
                    .id(companyId)
                    .status(status)
                    .build();

            companyService.updateById(company);
            //审核通过，更改状态后，放入redis中
            // redis哈希键初始化
            redisTemplate.opsForHash().put(company.getBrandName(), "", 0);
            return Result.success("审核通过");
        }
        else {
            return Result.error("审核拒绝");
        }
    }

    /**
     * 分页查询待审核公司
     * @param companyDto
     * @return
     */
    @ApiOperation("分页查询待审核公司")
    @GetMapping("/list")
    @PreAuthorize(value = "ps.hasPermission(T(com.ning.constants.SystemConstants).SYSTEM_DEPT_EDIT)")
    public Result<PageResult> getCheckCompanyList(CompanyDto companyDto){
        return companyService.getStatusList(companyDto);
    }


}