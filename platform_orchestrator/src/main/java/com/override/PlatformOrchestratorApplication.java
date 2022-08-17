package com.override;

import com.override.properties.NavbarProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties(NavbarProperties.class)
//@ComponentScan( basePackageClasses = FeignConfiguration.class)
public class PlatformOrchestratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlatformOrchestratorApplication.class, args);
    }

}
