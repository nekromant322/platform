package com.vapeshop.controllers;

import com.vapeshop.feigns.NotificatorFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

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
