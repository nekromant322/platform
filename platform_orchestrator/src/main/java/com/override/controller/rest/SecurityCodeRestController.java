package com.override.controller.rest;

import com.override.feign.NotificatorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityCodeRestController {

    @Autowired
    private NotificatorFeign notificatorFeign;

    @GetMapping("/securityCode/{login}")
    public String getSecurityCode(@PathVariable("login") String login) {
        return notificatorFeign.getSecurityCode(login);
    }
}
