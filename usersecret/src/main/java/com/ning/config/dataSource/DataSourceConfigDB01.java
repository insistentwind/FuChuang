package com.ning.config.dataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.ning.mapper.db01", sqlSessionFactoryRef = "db01SqlSessionFactory")
public class DataSourceConfigDB01 {


    @Bean("db01DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db01")
    public DataSource getDB01DataSource(){
        //使用Druid连接池时，用专门的DataSourceBuilder：
        return DruidDataSourceBuilder.create().build();
    }


    @Bean("db01SqlSessionFactory")
    public SqlSessionFactory db01SqlSessionFactory(@Qualifier("db01DataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        bean.setPlugins(interceptor);

        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO); // 设置 ID 生成策略
        globalConfig.setDbConfig(dbConfig);
        bean.setGlobalConfig(globalConfig);


        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db01/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean("db01SqlSessionTemplate")
    public SqlSessionTemplate db01SqlSessionTemplate(@Qualifier("db01SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
