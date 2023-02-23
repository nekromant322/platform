package com.override.feign;

import enums.CommunicationType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification")
public interface NotificatorFeign {

    @PostMapping("/communications")
    void sendMessage(
            @RequestParam("login") String login,
            @RequestParam("message") String message,
            @RequestParam("type") CommunicationType... type);
}
