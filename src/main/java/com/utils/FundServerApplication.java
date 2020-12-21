package com.utils;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.utils")
@EnableAsync
public class FundServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.utils.FundServerApplication.class, args);
    }

}