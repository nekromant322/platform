package com.override;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PlatformOrchestratorApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PlatformOrchestratorApplication.class, args);
    }

}
