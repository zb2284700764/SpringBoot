package com;

import com.common.persistence.annotation.MyBatisDao;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.modules.*.dao")
//@MapperScan(annotationClass = MyBatisDao.class)
//@ImportResource(locations = "classpath*:/spring-context.xml")
public class FirstApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }




}