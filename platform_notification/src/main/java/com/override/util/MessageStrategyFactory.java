package com.override.util;

//import enums.CommunicationStrategy;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageStrategyFactory {

    private MessageStrategy messageStrategy;

    @Autowired
    private EmailMessage emailMessage;
    @Autowired
    private TelegramMessage telegramMessage;

    public void setMessageStrategy(MessageStrategy messageStrategy) {
        this.messageStrategy = messageStrategy;
    }

    public Map<Communication, MessageStrategy> getSenderMap(Communication... types) {
        Map<Communication, MessageStrategy> senderMap = new HashMap<>();
        for (Communication type : types) {
            if (type.equals(Communication.TELEGRAM)) {
                setMessageStrategy(telegramMessage);
                senderMap.put(type, messageStrategy);
            } else if (type.equals(Communication.EMAIL)) {
                setMessageStrategy(emailMessage);
                senderMap.put(type, messageStrategy);
            }
        }
        return senderMap;
    }
}
