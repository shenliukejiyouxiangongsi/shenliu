package com.youdai.daichao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.youdai.daichao.project.*.*.mapper","com.youdai.daichao.mapper"})
public class DaichaoApplication  {

    public static void main(String[] args) {
        SpringApplication.run(DaichaoApplication.class, args);
    }

}
