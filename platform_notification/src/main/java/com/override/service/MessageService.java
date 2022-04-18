package com.override.service;

import com.override.models.Recipient;
import com.override.util.CommunicationStrategy;
import com.override.util.CommunicationStrategyFactory;
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
     * @return результат успешной работы (код 200) или ошибку связанную с отправкой сообщения (код 500)
     */
    public HttpStatus sendMessage(String login, String message, Communication... types) {
        Recipient recipient = recipientService.findRecipientByLogin(login);
        Map<Communication, CommunicationStrategy> senderMap = communicationStrategyFactory.getSenderMap();
        HttpStatus status;
        int httpStatusOk = 200;

        for (Communication type : types) {
            status = senderMap.get(type).sendMessage(recipient, message);
            if (status.value() == httpStatusOk) {
                return status;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }
}
