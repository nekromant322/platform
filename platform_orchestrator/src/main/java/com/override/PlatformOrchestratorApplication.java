package com.override;

import com.override.service.LessonStructureService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PlatformOrchestratorApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PlatformOrchestratorApplication.class, args);
//        List<String> stringList = new LinkedList<>(Arrays.asList("first", "second", "third", "fourth"));
//        ListIterator<String> stringListIterator = stringList.listIterator(stringList.size());
//        while (stringListIterator.hasPrevious()) {
//            System.out.println(stringListIterator.previous());
//        }
//        System.out.println("_____________");
//        stringListIterator = stringList.listIterator();
//        while (stringListIterator.hasNext()) {
//            System.out.println(stringListIterator.next());
//        }
    }

}
