//第一种方式：实现Job接口
//第一种方式：实现Job接口
package com.ning.taskjob;


import com.ning.constants.SystemConstants;
import com.ning.domain.entity.Work;
import com.ning.service.WorkService;
import com.ning.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//第二种方式：继承QuartzJobBean，重写executeInternal方法
@Component
@Slf4j
public class ViewCount extends QuartzJobBean {
    private WorkService workService;

    private RedisTemplate redisTemplate;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("定时任务执行");
        //获取redis中的浏览量
        Map<String,Integer> viewCount = redisTemplate.opsForHash().entries(SystemConstants.WORK_VIEW_COUNT);
        List<Work> collect = viewCount.entrySet().stream()
                .map(item -> new Work(Integer.valueOf(item.getKey()), item.getValue().longValue()))
                .collect(Collectors.toList());
        workService.updateBatchById(collect);
    }
    //设置为公共的，让配置类可以注入到里面
    public WorkService getWorkService() {
        return workService;
    }

    public void setWorkService(WorkService workService) {
        this.workService = workService;
    }

    public RedisTemplate getRedisTemplate(){
        return redisTemplate;
    }
    public void setRedisTemplate(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }
}
