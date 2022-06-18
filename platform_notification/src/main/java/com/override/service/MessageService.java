package com.override.service;

import com.override.exception.SendMessageException;
import com.override.exception.SmsRuException;
import com.override.model.Recipient;
import com.override.util.CommunicationStrategy;
import com.override.util.CommunicationStrategyFactory;
import enums.Communication;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
     * @param types   тип коммуникации с пользователем, например: TELEGRAM, SMS, EMAIL
     */
    public void sendMessage(String login, String message, Communication... types) {
        Recipient recipient = recipientService.findRecipientByLogin(login);
        Map<Communication, CommunicationStrategy> senderMap = communicationStrategyFactory.getSenderMap();

        if (types.length == 0) {
            throw new IllegalArgumentException("Не указаны типы коммуникации");
        }

        List<Object> exceptions = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            try {
                senderMap.get(types[i]).sendMessage(recipient, message);
                break;
            } catch (IllegalStateException | SmsRuException | MailSendException | FeignException e) {
                log.error("При попытке отправить сообщение по типу коммуникации \"{}\" произошла ошибка \"{}\"", types[i], e.getMessage());
                exceptions.add(e);
                if ((types.length - 1) == i) {
                    throw new SendMessageException("Произошли исключения во всех способах отправки сообщений: " + exceptions);
                }
            }
        }
    }

    public List<Communication> checkNotificationMethods(String login) {
        Recipient recipient = recipientService.findRecipientByLogin(login);
        List<Communication> communicationList1 = new ArrayList<>();

        if (recipient.getEmail().isPresent()) {
            communicationList1.add(Communication.valueOf("EMAIL"));
        }

        if (recipient.getPhoneNumber().isPresent()) {
            communicationList1.add(Communication.valueOf("SMS"));
        }

        if (recipient.getTelegramId().isPresent()) {
            communicationList1.add(Communication.valueOf("TELEGRAM"));
        }

        return communicationList1;
    }
}
