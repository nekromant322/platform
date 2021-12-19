package com.vapeshop.vape_notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VapeNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(VapeNotificationApplication.class, args);
    }

}
