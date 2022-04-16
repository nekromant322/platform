package com.override.controller;

import com.override.service.MessagesService;
import enums.Communications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/communications")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @PostMapping
    ResponseEntity<String> sendMessage(
            @RequestParam("user") String user,
            @RequestParam("message") String message,
            @RequestParam("type") Communications... type) {
        return messagesService.sendMessage(user, message, type);
    }
}
