package com.override.feigns;


import dtos.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification")
public interface NotificatorFeign {

    @GetMapping("/calls/balance")
    Double getBalance();

    @PostMapping("/teleMessages")
    void sendTelegramMessages(@RequestBody MessageDTO message);
}
