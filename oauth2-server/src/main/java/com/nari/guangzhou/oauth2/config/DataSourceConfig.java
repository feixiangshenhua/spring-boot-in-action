package com.nari.guangzhou.oauth2.config;

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
    @Bean(name = "metabaseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.metabase")
    public DataSource metabaseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "pi6000DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pi6000")
    public DataSource pi6000DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "metabaseJdbcTemplate")
    public JdbcTemplate metabaseJdbcTemplate() {
        return new JdbcTemplate(metabaseDataSource());
    }

    @Bean(name = "pi6000JdbcTemplate")
    public JdbcTemplate pi6000JdbcTemplate() {
        return new JdbcTemplate(pi6000DataSource());
    }

}
