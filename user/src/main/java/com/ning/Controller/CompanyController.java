package com.ning.Controller;

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
     * 公司端投递简历
     * 情景为：公司线下面试后，需要把面试者的信息投递到公司自己的职位里面去
     * 可能是一个List，导入
     * @param resumeVoList
     * @return
     */

    @ApiOperation("公司端投递简历(公司把面试者的信息录入)")
    @GetMapping("/resume")
    public Result<String> commitResumeList(List<ResumeVo> resumeVoList){
        return companyService.commitResumeList(resumeVoList);
    }

    /**
     * 公司注册
     * @param companyDo
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("公司注册")
    public Result<String> create(@RequestBody CompanySignUpDo companyDo){
        log.info("新增公司:{}",companyDo);
        return companyService.createCompany(companyDo);

    }

    /**
     * 删除公司
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除公司(应在管理端)")
    public Result<String> deleteBatch(@PathVariable List<Integer> ids){
        return companyService.deleteBatch(ids);
    }

    /**
     * 更新公司的信息
     * @param companyDo
     * @return
     */
    @ApiOperation("更新公司信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody CompanyDo companyDo){
        log.info("更改公司信息");
        CompanyDto companyDto = BeanCopyUtils.copyBean(companyDo, CompanyDto.class);
        return companyService.updateByCompany(companyDto);
    }
    /**
     * 查询此公司下所有职位投递的简历列表
     * @param
     * @return
     */
    @ApiOperation("此公司所有职位收到的简历列表")
    @GetMapping("/listByCompany")
    public Result<List<WorkVo>> getResumeListByCompany(){
        return companyService.getResumeListByCompany();
    }


    /**
     * 根据职位id查询所有投递的简历列表
     * @param id
     * @return
     */
    @ApiOperation("职位id查询投递的简历列表")
    @GetMapping("/listByResumeId/{id}")
    public Result<WorkVo> getResumeListByWorkId(@PathVariable Integer id){
        //这里的简历列表被放入到了WorkVo中的ResumeList中
        return companyService.getResumeListByWorkId(id);
    }
//    /**
//     * 根据用户id查询此用户的简历
//     * @return
//     */
//    @ApiOperation("据用户id查询此用户的简历")
//    @GetMapping("/getByUserId")
//    public Result<ResumeVo> getResumeVoByUserId(Integer userId){
//        return companyService.getResumeVoByUserId(userId);
//    }



    /**
     * 以上均需要放入公司端中
     */
    /**
     * 条件查询此公司下发布的职位
     * @param workPageVo
     * @return
     */
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
    @ApiOperation("根据公司名查询其行业")
    @GetMapping("/industry")
    public Result<List<String>> getIndustry(String companyBrand){
        return companyService.getIndustry(companyBrand);
    }
}
