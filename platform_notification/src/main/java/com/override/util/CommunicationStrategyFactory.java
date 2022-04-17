package com.override.util;

import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommunicationStrategyFactory {

    @Autowired
    private EmailCommunication emailCommunication;
    @Autowired
    private TelegramCommunication telegramCommunication;

    /**
     * При добавлении новых реализаций отправки сообщений юзерам в новые мессенджеры/соц. сети
     * нужно будет добавлять их в эту мапу
     * @return мапу со всеми способами отправки сообщений юзерам
     */
    @Autowired
    public Map<Communication, CommunicationStrategy> getSenderMap() {
        Map<Communication, CommunicationStrategy> senderMap = new HashMap<>();
        senderMap.put(emailCommunication.getType(), emailCommunication);
        senderMap.put(telegramCommunication.getType(), telegramCommunication);
        return senderMap;
    }
}
