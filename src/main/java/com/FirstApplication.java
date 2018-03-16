package com;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.modules.*.dao")
public class FirstApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }




}