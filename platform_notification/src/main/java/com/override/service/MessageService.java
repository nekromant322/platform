package com.override.service;

import com.override.models.Recipient;
import com.override.util.CommunicationStrategy;
import com.override.util.CommunicationStrategyFactory;
import enums.Communication;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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
     */
    public void sendMessage(String login, String message, Communication... types) {
        Recipient recipient = recipientService.findRecipientByLogin(login);
        Map<Communication, CommunicationStrategy> senderMap = communicationStrategyFactory.getSenderMap();

        if (types.length == 0) {
            throw new IllegalArgumentException("Не указаны типы коммуникации");
        }

        for (int i = 0; i < types.length; i++) {
            try {
                senderMap.get(types[i]).sendMessage(recipient, message);
                break;
            } catch (IllegalArgumentException | FeignException e) {
                if ((types.length - 1) == i) {
                    throw new IllegalArgumentException("Сообщение не доставлено, т.к. были вызваны исключения во всех способах отправки");
                }
                log.error("При попытке отправить сообщение по типу коммуникации \"{}\" произошла ошибка \"{}\"", types[i], e.getMessage());
            }
        }
    }
}
