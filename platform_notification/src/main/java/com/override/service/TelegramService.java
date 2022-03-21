package com.override.service;

import com.override.feign.TelegramFeign;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramService {
    @Autowired
    TelegramFeign telegramFeign;

    public void sendMessage(MessageDTO messageDTO){
        telegramFeign.sendMessages(messageDTO);
    }
}
