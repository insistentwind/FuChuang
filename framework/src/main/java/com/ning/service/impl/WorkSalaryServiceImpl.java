package com.ning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.WorkSalary;
import com.ning.service.WorkSalaryService;
import com.ning.mapper.WorkSalaryMapper;
import org.springframework.stereotype.Service;

/**
 * (WorkSalary)表服务实现类
 *
 * @author makejava
 * @since 2024-03-28 17:04:21
 */
@Service("workSalaryService")
public class WorkSalaryServiceImpl extends ServiceImpl<WorkSalaryMapper, WorkSalary> implements WorkSalaryService {

}

