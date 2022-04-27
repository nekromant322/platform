package com.override.util;

import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommunicationStrategyFactory {

    @Autowired
    private List<CommunicationStrategy> strategyList;

    @Autowired
    public Map<Communication, CommunicationStrategy> getSenderMap() {
        Map<Communication, CommunicationStrategy> senderMap = new HashMap<>();
        for (CommunicationStrategy communicationStrategy : strategyList) {
            senderMap.put(communicationStrategy.getType(), communicationStrategy);
        }
        return senderMap;
    }
}
