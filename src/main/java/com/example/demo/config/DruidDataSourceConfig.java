package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;


/**
 * druid配置类
 *
 * @author guochao
 * @since 1.0.0
 */
@Configuration
public class DruidDataSourceConfig {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Bean
    /**
     *
     * 在同样的DataSource中，首先使用被标注的DataSource
     * */
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUsername(propertiesConfig.getDruid().getUsername());
        dataSource.setPassword(propertiesConfig.getDruid().getPassword());
        dataSource.setUrl(propertiesConfig.getDruid().getUrl());
        dataSource.setDriverClassName(propertiesConfig.getDruid().getDriverClassName());
        try {
            dataSource.setFilters(propertiesConfig.getDruid().getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setInitialSize(propertiesConfig.getDruid().getInitialSize());
        dataSource.setMaxActive(propertiesConfig.getDruid().getMaxActive());
        dataSource.setTimeBetweenEvictionRunsMillis(propertiesConfig.getDruid().getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(propertiesConfig.getDruid().getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(propertiesConfig.getDruid().getValidationQuery());
        dataSource.setTestWhileIdle(propertiesConfig.getDruid().isTestWhileIdle());
        dataSource.setTestOnBorrow(propertiesConfig.getDruid().isTestOnBorrow());
        dataSource.setTestOnReturn(propertiesConfig.getDruid().isTestOnReturn());

        return dataSource;
    }
}
