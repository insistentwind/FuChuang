package com.ning.Controller;

import com.ning.domain.dto.CompanyDto;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.CompanyVo;
import com.ning.service.CompanyService;
import com.ning.service.impl.CompanyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: qjn
 * @Date: 2024/1/15 18:07
 */
@RestController
@Api(tags = "公司相关接口")
@Slf4j
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    /**
     * 分页查询所有公司
     * @param companyDto
     * @return
     */
    @ApiOperation("分页查询所有公司")
    @GetMapping("/page")
    public Result<PageResult> page(CompanyDto companyDto){
        log.info("分页查询条件:{}",companyDto);
        return companyService.getListByDto(companyDto);
    }

    /**
     * 根据id查询公司
     * @param id
     * @return
     */
    @ApiOperation("根据id查询公司")
    @GetMapping("/{id}")
    public Result<CompanyVo> getCompanyByTag(@PathVariable Integer id){
        log.info("查询的公司id是：{}",id);
        return companyService.getCompanyById(id);
    }
}
