package com.ning.runner;

import com.ning.domain.entity.Work;
import com.ning.constants.SystemConstants;
import com.ning.service.WorkService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @Author: qjn
 * @Date: 2024/2/1 16:38
 * 设置redis初始时，表中存放的浏览量数据
 * 开机自启
 */
@Component
public class ViewCountRunner implements CommandLineRunner{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ViewCountRunner.class);
    @Autowired
    private WorkService workService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("放入职位浏览量 " + dateTimeFormatter.format(LocalDateTime.now()));
        //获取到所有工作职位的信息
        List<Work> workList = workService.list();
        Map<String, Integer> collect = workList.stream().collect(Collectors.toMap(
                work -> work.getId().toString(),
                work -> work.getViewCount().intValue()
        ));
        redisTemplate.opsForHash().putAll(SystemConstants.WORK_VIEW_COUNT,collect);
        //连接服务器启动较慢的原因是: 职位数据有10w条，把每条数据放入redis需要时间
        System.out.println("浏览量放入完成 " + dateTimeFormatter.format(LocalDateTime.now()));
    }
}
