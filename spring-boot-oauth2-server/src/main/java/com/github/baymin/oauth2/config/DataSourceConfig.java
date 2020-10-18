package com.github.baymin.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 数据源配置类
 *
 * @author Zongwei
 * @date 2019/9/29 9:16
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "oauth2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.oauth2")
    public DataSource oauth2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oauth2JdbcTemplate")
    public JdbcTemplate oauth2JdbcTemplate() {
        return new JdbcTemplate(oauth2DataSource());
    }

}
