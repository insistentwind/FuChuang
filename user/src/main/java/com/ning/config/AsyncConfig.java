package com.ning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author: qjn
 * @create: 2024/03/29 23:06
 **/
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "commonAsyncThreadPool")
    public ThreadPoolTaskExecutor commonAsyncThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 设置核心线程数
        executor.setMaxPoolSize(20); // 设置最大线程数
        executor.setQueueCapacity(100); // 设置队列容量
        executor.setThreadNamePrefix("CommonAsyncThread-"); // 设置线程名称前缀
        executor.initialize(); // 初始化线程池
        return executor;
    }

}