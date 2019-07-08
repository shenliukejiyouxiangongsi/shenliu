package com.youdai.daichao.framework.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/12
 * @Description  分页配置
 */
//@Configuration
////扫描dao或者是Mapper接口
//@MapperScan("com.youdai.daichao.project.*.*.mapper")
public class MybatisPlusConfig {
    /**
     * mybatis-plus 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

}
