package com.accompany;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.accompany.mapper")
public class AccompanyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccompanyServerApplication.class, args);
    }

}
