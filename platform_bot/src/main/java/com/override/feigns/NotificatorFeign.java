package com.override.feigns;

import enums.Communication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification")
public interface NotificatorFeign {

    @PostMapping("/communications")
    ResponseEntity<String> sendMessage(
            @RequestParam("login") String login,
            @RequestParam("message") String message,
            @RequestParam("type") Communication... type);
}
