package com.override.feign;

import dtos.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bot")
@RequestMapping("/messages")
public interface TelegramFeign {
    @PostMapping
    void sendMessages(@RequestParam String message, @RequestParam String chatId);
}
