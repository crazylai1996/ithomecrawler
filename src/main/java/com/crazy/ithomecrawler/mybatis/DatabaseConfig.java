package com.crazy.ithomecrawler.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author: Sisu
 * @Description:
 * @Date: Created in 23:57 2017-08-26 0026
 * @Modified By:
 *
 **/
@Configuration
@MapperScan(basePackages = "com.crazy.ithomecrawler.mybatis.mapper")
public class DatabaseConfig {
    /**
     * 数据源配置
     * @return
     */
    @Bean
    public DataSource druidDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("mysql");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ithome");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
