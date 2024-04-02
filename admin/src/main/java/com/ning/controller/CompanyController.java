package com.ning.controller;

import com.ning.domain.Do.CompanyDo;
import com.ning.domain.Do.CompanySignUpDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.result.Result;
import com.ning.service.CompanyService;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: qjn
 * @create: 2024/03/16 18:31
 **/
@RestController
@Api(tags = "公司账号接口")
@Slf4j
@RequestMapping("/system/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;


//    /**
//     * 批量删除公司员工
//     * @param ids
//     * @return
//     */
//    @ApiOperation("批量删除公司员工")
//    @DeleteMapping
//    public Result<String> deleteByIds(List<Integer> ids){
//        return companyService.deleteByIds(ids);
//    }

//    /**
//     * 新增公司员工
//     * @param userRoleVo
//     * @return
//     */
    // todo 搁置
//    @ApiOperation("新增公司员工")
//    @PutMapping("/add")
//    public Result<String> addEmployee(UserRoleVo userRoleVo){
//        return companyService.addEmployee(userRoleVo);
//    }


    // todo 人才仓库?

    /**
     * 更新公司的信息
     *
     * @param companyDo
     * @return
     */
    @ApiOperation("更新公司信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody CompanyDo companyDo) {
        log.info("更改公司信息");
        CompanyDto companyDto = BeanCopyUtils.copyBean(companyDo, CompanyDto.class);
        return companyService.updateByCompany(companyDto);
    }


    /**
     * 公司注册
     *
     * @param companyDo
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("公司注册")
    public Result<String> create(@RequestBody CompanySignUpDo companyDo) {
        log.info("新增公司:{}", companyDo);
        return companyService.createCompany(companyDo);

    }




}