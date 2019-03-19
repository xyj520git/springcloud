package com.xyj.beans.datapermission;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis数据权限配置
 */
@Configuration
public class MyBatisBeansConfig {
    @Bean
    public Interceptor interceptor() {
        return new PermInterceptor();
    }
}
