package com.youdai.daichao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.youdai.daichao.mapper")
public class DaichaoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaichaoApiApplication.class, args);
    }

}
