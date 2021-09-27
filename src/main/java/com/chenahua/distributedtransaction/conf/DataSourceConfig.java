package com.chenahua.distributedtransaction.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {



    @Bean(name = "dataSourceOne")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource getDataSourceOne() {
        DruidDataSource build = DruidDataSourceBuilder.create().build();
        return build;
    }


    @Bean(name = "dataSourceTwo")
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource getDataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired @Qualifier("dynamicRoutingDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Autowired @Qualifier("dynamicRoutingDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}

