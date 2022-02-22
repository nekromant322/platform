package com.override;

import com.override.service.LessonStructureService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PlatformOrchestratorApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PlatformOrchestratorApplication.class, args);
        LessonStructureService lessonStructureService = context.getBean(LessonStructureService.class);
        try {
            lessonStructureService.scanLessonStructure("core");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
