package com.override.controller;

import com.override.service.TelegramService;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teleMessages")
public class TelegramSendController {
    @Autowired
    private TelegramService telegramService;

    @PostMapping
    public void sendTelegramMessages(@RequestBody MessageDTO messageDTO){
        telegramService.sendMessage(messageDTO);
    }
}
