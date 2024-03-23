package com.ning.controller;

import com.ning.domain.Do.CompanyDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.CompanyVo;
import com.ning.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/16 18:31
 **/
@RestController
@Api(tags = "公司管理接口")
@RequestMapping("/system/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @ApiOperation("分页查询公司hr信息")
    @GetMapping("/page")
    public Result<PageResult> getHrByPage(CompanyDto companyDto){
        return companyService.getListByDto(companyDto);
    }

    /**
     * 批量删除公司hr
     * @param ids
     * @return
     */
    @ApiOperation("批量删除公司hr")
    @DeleteMapping
    public Result<String> deleteByIds(List<Integer> ids){
        return companyService.deleteByIds(ids);
    }
}