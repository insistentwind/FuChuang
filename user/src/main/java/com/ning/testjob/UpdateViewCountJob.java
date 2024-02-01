package com.ning.testjob;

import com.mysql.cj.result.IntegerValueFactory;
import com.ning.domain.entity.Work;
import com.ning.domain.systemConstants.SystemConstants;
import com.ning.service.WorkService;
import com.ning.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: qjn
 * @Date: 2024/2/1 16:53
 * 设置把redis中信息更新到数据库中的时间间隔
 */
@Component
@Slf4j
public class UpdateViewCountJob {
    @Autowired
    private WorkService workService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisTemplate redisTemplate;
    //一分钟一次
    @Scheduled(cron = "0/60 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String,Integer> viewCount = redisTemplate.opsForHash().entries(SystemConstants.WORK_VIEW_COUNT);
        List<Work> collect = viewCount.entrySet().stream()
                .map(item -> new Work(Integer.valueOf(item.getKey()), item.getValue().longValue()))
                .collect(Collectors.toList());
        workService.updateBatchById(collect);
    }
}
