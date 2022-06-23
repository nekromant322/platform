package com.override.service;

import com.override.PlatformBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private PlatformBot platformBot;

    public void sendMessage(String message, String chatId) {
        platformBot.sendMessage(chatId, message);
    }
}
