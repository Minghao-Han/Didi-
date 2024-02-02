package com.hmh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableEurekaClient //开启Eureka客户端 deprecated
@EnableFeignClients(basePackages = {"com.hmh"})
@EnableDiscoveryClient
public class BillMain {
    public static void main(String[] args) {
        SpringApplication.run(BillMain.class,args);
    }
}
