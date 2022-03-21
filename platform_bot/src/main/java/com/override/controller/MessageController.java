package com.override.controller;

import com.override.services.MessageService;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody MessageDTO messageDTO){
        messageService.sendMessage(messageDTO.getMessage(), messageDTO.getChatId());
    }

}
