package com.override.controller;

import com.override.service.TelegramService;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HttpStatus> sendTelegramMessages(@RequestBody MessageDTO messageDTO){
        return telegramService.sendMessage(messageDTO);
    }
}
