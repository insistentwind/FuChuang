package com.ning.controller;

import com.ning.annotation.SecurityParameter;
import com.ning.constants.SystemConstants;
import com.ning.domain.Do.CompanyDo;
import com.ning.domain.Do.CompanySignUpDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.CompanyVo;
import com.ning.domain.vo.ResumeVo;
import com.ning.domain.vo.WorkPageVo;
import com.ning.domain.vo.WorkVo;
import com.ning.exception.BaseException;
import com.ning.service.CompanyService;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
     * todo 新增和删除公司应该在管理端作业
     * 还有更新公司
     */

    /**
     * 新增公司
     * 一个公司只能有一个账号，如果这个公司有多个行业，则多个行业下也要分别创建账号
     * @param companyDo
     * @return
     */



    /**
     * 以上均需要放入公司端中
     */
    /**
     * 条件查询此公司下发布的职位
     * @param workPageVo
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("条件查询此公司下的职位")
    @GetMapping("/PositionList")
    public Result<List<WorkVo>> pageByCategoryId(WorkPageVo workPageVo){
        return companyService.pageByCategoryId(workPageVo);
    }



    /**
     * 分页查询所有公司
     * @param companyDto
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("分页查询公司")
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
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("id查询公司")
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
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("据公司名称查询公司信息")
    @GetMapping("/search")
    public Result<CompanyVo> getByName(@RequestParam String companyName) {
        if (!StringUtils.hasText(companyName)){
            throw new BaseException("请检查输入");
        }
        return companyService.getByCompanyName(companyName);
    }

    /**
     * 根据公司名查询其行业
     * @return
     */
    @SecurityParameter(inDecode = SystemConstants.IN_DECODE_BUTTON,outEncode = SystemConstants.OUT_ENCODE_BUTTON)
    @ApiOperation("根据公司名查询其行业")
    @GetMapping("/industry")
    public Result<List<String>> getIndustry(String companyBrand){
        return companyService.getIndustry(companyBrand);
    }
}
