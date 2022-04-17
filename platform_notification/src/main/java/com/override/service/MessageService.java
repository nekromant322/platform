package com.override.service;

import com.override.models.Recipient;
import com.override.util.MessageStrategy;
import com.override.util.MessageStrategyFactory;
import enums.Communication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private RecipientService recipientService;
    @Autowired
    private MessageStrategyFactory messageStrategyFactory;

    /**
     * Метод, в котором определяется каким способом будет отправлено сообщение. Если найдено несколько
     * типов коммуникации с пользователем, то сообщение будет отправлено по первому предложенному
     * в аргументах типу
     *
     * @param login   логин пользователя, которому будет отправлено сообщение
     * @param message текст сообщения
     * @param types   тип коммуникации с пользователем, например: TELEGRAM, VK, EMAIL
     * @return результат успешной работы или ошибку связанную с отправкой сообщения
     */
    public ResponseEntity<HttpStatus> sendMessage(String login, String message, Communication... types) {
        Recipient recipient = recipientService.getRecipientByLogin(login);
        Map<Communication, MessageStrategy> senderMap = messageStrategyFactory.getSenderMap(types);
        ResponseEntity<HttpStatus> status = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        for (Communication type : types) {
            status = senderMap.get(type).sendMessage(recipient, message);
            if (status.getStatusCode().value() == 200) {
                return status;
            }
        }
        return status;
    }
}
