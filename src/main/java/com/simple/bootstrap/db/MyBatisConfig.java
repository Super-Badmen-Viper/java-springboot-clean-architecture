package com.simple.bootstrap.db;

import com.simple.repository.auth.UserMybatisMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public MapperFactoryBean<UserMybatisMapper> userMybatisMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<UserMybatisMapper> factoryBean = new MapperFactoryBean<>(UserMybatisMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
