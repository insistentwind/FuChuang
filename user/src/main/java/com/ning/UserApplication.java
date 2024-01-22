package com.ning;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: qjn
 * @Date: 2024/1/9 0:47
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.ning.mapper")
@Slf4j
@EnableSwagger2
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        log.info("用户端启动");
    }
}
