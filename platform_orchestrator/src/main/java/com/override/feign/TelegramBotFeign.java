package com.override.feign;

import dto.ResponseJoinRequestDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "bot")
@RequestMapping("/register")
public interface TelegramBotFeign {

    @PostMapping
    void responseForRequest(@RequestBody ResponseJoinRequestDTO responseJoinRequestDTO);
}
