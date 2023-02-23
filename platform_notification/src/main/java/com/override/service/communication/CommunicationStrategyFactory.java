package com.override.service.communication;

import enums.CommunicationType;
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
    public Map<CommunicationType, CommunicationStrategy> getSenderMap() {
        Map<CommunicationType, CommunicationStrategy> senderMap = new HashMap<>();
        for (CommunicationStrategy communicationStrategy : strategyList) {
            senderMap.put(communicationStrategy.getType(), communicationStrategy);
        }
        return senderMap;
    }
}
