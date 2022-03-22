package com.override.util;

import com.override.models.HelloWorldModel;
import com.override.service.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitializationService implements CommandLineRunner {
    @Autowired
    HelloWorldService helloWorldService;

    @Override
    public void run(String... args) throws Exception {
        HelloWorldModel helloWorldModel;
        for (int i = 0; i < 5; i++) {
            helloWorldModel = new HelloWorldModel("Hi planet #" + i);
            helloWorldService.save(helloWorldModel);
            log.info("HelloWorldModel with id={} and data '{}' created", helloWorldModel.getId(), helloWorldModel.getData());
        }
    }
}
