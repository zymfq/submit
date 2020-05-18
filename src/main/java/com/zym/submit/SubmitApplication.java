package com.zym.submit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zym.submit.mapper")
public class SubmitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubmitApplication.class, args);
    }

}
