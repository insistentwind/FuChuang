package com.ning.controller;

import com.ning.constants.SystemConstants;
import com.ning.domain.Do.CompanyDo;
import com.ning.domain.Do.CompanySignUpDo;
import com.ning.domain.dto.CompanyDto;
import com.ning.domain.entity.Ack;
import com.ning.domain.result.Result;
import com.ning.domain.vo.ResumeVo;
import com.ning.service.AckService;
import com.ning.service.CompanyService;
import com.ning.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 请求查看用户简历
     * @param userId
     * @return
     */
    @ApiOperation("请求查看用户简历")
    @GetMapping("/plz/{userId}")
    public Result<String> sendRequestToUser(@PathVariable Integer userId){
        return companyService.sendRequestToUser(userId);
    }


    @Autowired
    private AckService ackService;
    /**
     * 查看所有收到的查看简历申请
     * @return
     */
    @ApiOperation("查看所有收到的简历查看申请")
    @GetMapping("/allApplications")
    public Result<List<Ack>> getCompanyMessage(){
        return ackService.getCompanyAll();
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
 * TODO 2024/4/3 设置公司请求用户，查看用户的简历信息消息
 * 用户要同意才回显
 * todo 用户同意的接口 发送消息的接口
 */

}