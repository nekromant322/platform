package com.override.service;

import com.override.models.Recipient;
import dtos.MailDTO;
import dtos.MessageDTO;
import enums.Communications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class MessagesService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private TelegramService telegramService;
    @Autowired
    private RecipientService recipientService;

    public ResponseEntity<HttpStatus> sendToTelegram(Recipient recipient, String message) {
        telegramService.sendMessage(MessageDTO.builder()
                .chatId(recipient.getTelegramId())
                .message(message)
                .build());
        log.info("The message was sent to {} in Telegram", recipient.getLogin());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> sendByEmail(Recipient recipient, String message) {
        emailService.sendSimpleMail(MailDTO.builder()
                .to(Collections.singletonList(recipient.getEmail()))
                .text(message)
                .subject("test")
                .build());
        log.info("The message was sent to {} by email", recipient.getLogin());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод, в котором определяется каким способом будет отправлено сообщение. Если найдено несколько
     * типов коммуникации с пользователем, то сообщение будет отправлено по первому предложенному
     * в аргументах типу
     *
     * @param login   логин пользователя, которому будет отправлено сообщение
     * @param message текст сообщения
     * @param communications    тип коммуникации с пользователем, например: TELEGRAM, VK, EMAIL
     * @return результат успешной работы или ошибку связанную с отправкой сообщения
     */
    public ResponseEntity<HttpStatus> sendMessage(String login, String message, Communications... communications) {
        Recipient recipient = recipientService.getRecipientByLogin(login);
        for (Communications type : communications) {
            if (!recipient.getTelegramId().isEmpty() && type.equals(Communications.TELEGRAM)) {
                return sendToTelegram(recipient, message);
            }
            if (!recipient.getEmail().isEmpty() && type.equals(Communications.EMAIL)) {
                return sendByEmail(recipient, message);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
