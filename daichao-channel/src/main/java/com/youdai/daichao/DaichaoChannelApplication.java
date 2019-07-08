package com.youdai.daichao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.youdai.daichao.project.*.*.mapper")
@ServletComponentScan
public class DaichaoChannelApplication  {

    public static void main(String[] args) {
        SpringApplication.run(DaichaoChannelApplication.class, args);
    }

}
