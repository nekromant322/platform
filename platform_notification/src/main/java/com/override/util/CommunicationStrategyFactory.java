package com.override.util;

import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommunicationStrategyFactory {

    private CommunicationStrategy communicationStrategy;

    @Autowired
    private EmailCommunication emailMessage;
    @Autowired
    private TelegramCommunication telegramMessage;

    private void setMessageStrategy(CommunicationStrategy communicationStrategy) {
        this.communicationStrategy = communicationStrategy;
    }

    /**
     * При добавлении новых реализаций отправки сообщений юзерам в новые мессенджеры/соц. сети
     * нужно будет добавлять их в эту мапу
     * @return мапу со всеми способами отправки сообщений юзерам
     */
    public Map<Communication, CommunicationStrategy> getSenderMap() {
        Map<Communication, CommunicationStrategy> senderMap = new HashMap<>();

        setMessageStrategy(telegramMessage);
        senderMap.put(Communication.TELEGRAM, communicationStrategy);
        setMessageStrategy(emailMessage);
        senderMap.put(Communication.EMAIL, communicationStrategy);
        return senderMap;
    }
}
