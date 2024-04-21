package com.ning.config;

import com.ning.domain.entity.Work;
import com.ning.service.WorkService;
import com.ning.taskjob.ViewCount;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.logging.Logger;

/**
 * @author: qjn
 * @create: 2024/04/11 14:26
 **/
@Configuration
@Slf4j
public class QuartzConfig {
    // MyJob2 需要 workService， 这两个都可以注入
    @Autowired
    private WorkService workService;
    @Autowired
    private RedisTemplate redisTemplate;

    // MyJob2任务配置
    // 传参
    @Bean
    JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(ViewCount.class);
        JobDataMap map = new JobDataMap();
        map.put("workService", workService);
        map.put("redisTemplate",redisTemplate);
        bean.setJobDataMap(map);
        return bean;
    }
    //定义执行时间
    @Bean
    CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setCronExpression("0 0 * * * ?");
        bean.setJobDetail(jobDetailFactoryBean().getObject());
        return bean;
    }

    // 添加 定时任务的触发器
    @Bean
    SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(cronTriggerFactoryBean().getObject());
        return bean;
    }
}