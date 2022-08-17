package com.override.controller;

import com.override.service.MessageService;
import dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody MessageDTO message){
       messageService.sendMessage(message.getMessage(), message.getChatId());
    }
}
