package com.override.service;

import com.override.models.Recipient;
import com.override.util.CommunicationStrategy;
import com.override.util.CommunicationStrategyFactory;
import com.override.util.EmailCommunication;
import com.override.util.TelegramCommunication;
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
    private CommunicationStrategyFactory communicationStrategyFactory;

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
        Map<Communication, CommunicationStrategy> senderMap = communicationStrategyFactory.getSenderMap();
        ResponseEntity<HttpStatus> status = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        int httpStatusOk = 200;

        for (Communication type : types) {
            status = senderMap.get(type).sendMessage(recipient, message);
            if (status.getStatusCode().value() == httpStatusOk) {
                return status;
            }
        }
        return status;
    }
}
