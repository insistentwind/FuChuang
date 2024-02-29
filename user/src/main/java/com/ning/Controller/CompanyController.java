package com.ning.Controller;

import com.ning.domain.Do.CompanyDo;
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
     * 新增公司
     * @param companyDo
     * @return
     */
    @PostMapping
    @ApiOperation("新增公司")
    public Result<String> create(@RequestBody CompanyDo companyDo){
        log.info("新增公司:{}",companyDo);
        return companyService.createCompany(companyDo);

    }

    /**
     * 删除公司
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除公司")
    public Result<String> deleteBatch(@PathVariable List<Integer> ids){
        return companyService.deleteBatch(ids);
    }

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
     * 更新公司的信息
     * @param companyDto
     * @return
     */
    @ApiOperation("更新公司信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody CompanyDto companyDto){
        log.info("更改公司信息");
        return companyService.updateByCompany(companyDto);
    }

    /**
     * 根据id查询公司
     * @param id
     * @return
     */
    @ApiOperation("根据id查询公司")
    @GetMapping("/{id}")
    public Result<CompanyVo> getCompanyById(@PathVariable Integer id){
        log.info("查询的公司id是：{}",id);
        return companyService.getCompanyById(id);
    }

    /**
     * 根据公司名称查询公司信息（公司名称是唯一的，只查出一条记录）
     * @param companyName
     * @return
     */
    @ApiOperation("根据公司名称查询公司信息")
    @GetMapping("/search")
    public Result<CompanyVo> getByName(@RequestParam String companyName) {
        return companyService.getByCompanyName(companyName);
    }

}
