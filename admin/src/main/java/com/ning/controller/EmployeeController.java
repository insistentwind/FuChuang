package com.ning.controller;

import com.ning.service.CompanyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: qjn
 * @create: 2024/03/30 22:31
 **/
@RestController
@RequestMapping("/system/employee")
@Slf4j
@Api(tags = "公司员工")
public class EmployeeController {

    @Autowired
    private CompanyService companyService;
}