package com.override.feign;

import dto.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bot")
public interface TelegramFeign {
    @PostMapping("/messages")
    void sendMessage(@RequestBody MessageDTO message);
}
