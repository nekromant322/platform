package com.vapeshop.controllers;

import com.vapeshop.controllers.feigns.NotificatorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private NotificatorFeign proxy;

    @GetMapping
    @RequestMapping("/main")
    public String getMessage() {
        return "new controller";
    }

    @GetMapping
    @RequestMapping("/feign")
    public String getFeignMessage() {
        return proxy.getMessage();
    }
}
