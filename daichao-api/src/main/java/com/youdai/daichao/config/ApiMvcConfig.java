package com.youdai.daichao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/14
 * @Description
 */
@Configuration
public class ApiMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ApiInterceptor   getApiInterceptor(){

        return new ApiInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         /*addPathPatterns 用于添加拦截规则
         excludePathPatterns 用户排除拦截*/
        registry.addInterceptor(getApiInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/api/userRecord/put");
    }

}
