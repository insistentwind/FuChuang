package com.ning.runner;

import com.ning.domain.entity.Company;
import com.ning.domain.entity.Follow;
import com.ning.service.CompanyService;
import com.ning.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/02/29 21:57
 * 此启动类是为了在程序启动时，把各个公司的关注者都放入redis中，以便后续操作
 **/
@Slf4j
@Component
public class CompanyFollowerRunner implements CommandLineRunner {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private FollowService followService;
    @Override
    public void run(String... args) throws Exception {
        log.info("开始执行开机关注任务");
        List<Follow> followList = followService.list();
        followList.forEach(item -> {
            Integer companyId = item.getCompanyId();
            Integer userId = item.getUserId();
            Company company = companyService.getById(companyId);
            String companyName = company.getBrandName();
            redisTemplate.opsForHash().put(companyName,userId.toString(),userId);
        });
    }
}