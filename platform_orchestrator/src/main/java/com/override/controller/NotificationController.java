package com.override.controller;

import com.override.feigns.NotificatorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    NotificatorFeign notificatorFeign;

    @Autowired
    public NotificationController(NotificatorFeign notificatorFeign) {
        this.notificatorFeign = notificatorFeign;
    }

    @Value("${sms.url.replenish-balance}")
    private String urlToReplenishBalance;

    @GetMapping("/balance")
    public String getBalance() {
        return notificatorFeign.getBalance();
    }

    @GetMapping("/url/replenish")
    public String getUrlToReplenishBalance() {
        return urlToReplenishBalance;
    }
}
