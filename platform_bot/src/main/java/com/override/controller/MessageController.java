package com.override.controller;

import com.override.services.MessageService;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestParam String chatId, @RequestParam String message){
        messageService.sendMessage(message, chatId);
    }
}
