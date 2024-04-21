package com.ning.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.ning.mapper.db02", sqlSessionFactoryRef = "db02SqlSessionFactory")
public class DataSourceConfigDB02 {

    @Bean("db02DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db02")
    public DataSource getDB02DataSource(){
        //使用Druid连接池时，用专门的DataSourceBuilder：
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("db02SqlSessionFactory")
    public SqlSessionFactory db02SqlSessionFactory(@Qualifier("db02DataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        bean.setPlugins(interceptor);

        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO); // 设置 ID 生成策略
        globalConfig.setDbConfig(dbConfig);
        bean.setGlobalConfig(globalConfig);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db02/*.xml"));
        return bean.getObject();
    }

    @Bean("db02SqlSessionTemplate")
    public SqlSessionTemplate db02SqlSessionTemplate(@Qualifier("db02SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
}
