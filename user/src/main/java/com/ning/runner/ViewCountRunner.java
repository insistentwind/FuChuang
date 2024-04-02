package com.ning.runner;

import com.ning.domain.entity.Work;
import com.ning.constants.SystemConstants;
import com.ning.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: qjn
 * @Date: 2024/2/1 16:38
 * 设置redis初始时，表中存放的浏览量数据
 * 开机自启
 */
@Component
public class ViewCountRunner implements CommandLineRunner{
    @Autowired
    private WorkService workService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Redis程序初始化");
        //获取到所有工作职位的信息
        List<Work> workList = workService.list();
        Map<String, Integer> collect = workList.stream().collect(Collectors.toMap(
                work -> work.getId().toString(),
                work -> work.getViewCount().intValue()
        ));
        redisTemplate.opsForHash().putAll(SystemConstants.WORK_VIEW_COUNT,collect);
    }
}
