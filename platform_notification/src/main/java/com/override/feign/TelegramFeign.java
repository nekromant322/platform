package com.override.feign;

import dtos.MessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "bot")
@RequestMapping("/messages")
public interface TelegramFeign {
    @PostMapping
    ResponseEntity<String> sendMessage(@RequestBody MessageDTO message);
}
