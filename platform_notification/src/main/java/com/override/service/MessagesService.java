package com.override.service;

import com.override.models.Recipient;
import dtos.MailDTO;
import dtos.MessageDTO;
import enums.Communications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.logging.Logger;

@Service
public class MessagesService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private TelegramService telegramService;
    @Autowired
    private RecipientService recipientService;

    public ResponseEntity<String> sendMessage(String login, String message, Communications... communications) {
        Recipient recipient = recipientService.getRecipientByLogin(login);
        for (Communications type : communications) {
            if (!recipient.getTelegramId().isEmpty() && type.equals(Communications.TELEGRAM)) {
                telegramService.sendMessage(MessageDTO.builder()
                        .chatId(recipient.getTelegramId())
                        .message(message)
                        .build());
                Logger.getGlobal().info("tg");
                break;
            }
            if (!recipient.getEmail().isEmpty() && type.equals(Communications.EMAIL)) {
                emailService.sendSimpleMail(MailDTO.builder()
                        .to(Collections.singletonList(recipient.getEmail()))
                        .text(message)
                        .subject("test")
                        .build());
                Logger.getGlobal().info("email");
                break;
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
