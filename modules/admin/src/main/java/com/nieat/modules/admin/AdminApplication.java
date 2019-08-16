package com.nieat.modules.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.nieat"})
@MapperScan(basePackages = {"com.nieat.**.dao"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class);
    }
}
