package com.override.feign;

import dtos.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "bot")
@RequestMapping("/messages")
public interface TelegramFeign {
    @PostMapping
    void sendMessages(@RequestBody MessageDTO messageDTO);
}
