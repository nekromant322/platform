package com.override.controllers;

import com.override.feigns.NotificatorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//TODO убрать после объяснения шнайдеру
public class SampleController {

    @Autowired
    private NotificatorFeign notificationFeign;

    @GetMapping
    @RequestMapping("/main")
    public String getMessage() {
        return "new controller";
    }

    @GetMapping
    @RequestMapping("/feign")
    public String getFeignMessage() {
        return notificationFeign.getMessage();
    }
}
