package com.override.feign;

import dto.MessageDTO;
import dto.ResponseJoinRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bot")
public interface TelegramBotFeign {

    @PostMapping("/register")
    void responseForRequest(@RequestBody ResponseJoinRequestDTO responseJoinRequestDTO);

    @PostMapping("/messages")
    void sendMessage(@RequestBody MessageDTO message);
}

