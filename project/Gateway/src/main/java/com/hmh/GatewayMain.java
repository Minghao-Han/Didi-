package com.hmh;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GatewayMain.class, args);
    }
}
