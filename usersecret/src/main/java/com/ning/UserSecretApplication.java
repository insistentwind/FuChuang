package com.ning;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.ning.mapper")
@Slf4j
@EnableSwagger2
@EnableScheduling
// 启动异步服务
@EnableAsync
public class UserSecretApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserSecretApplication.class, args);
        log.info("密钥端启动");
    }
}