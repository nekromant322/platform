package com.override;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlatformNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformNotificationApplication.class, args);
    }

}
