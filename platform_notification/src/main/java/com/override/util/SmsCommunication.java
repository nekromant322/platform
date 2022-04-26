package com.override.util;

import com.override.models.Recipient;
import com.override.service.SmsRuServiceImpl;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsCommunication implements CommunicationStrategy {

    @Autowired
    private SmsRuServiceImpl smsService;

    @Override
    public void sendMessage(Recipient recipient, String message) {
        smsService.sendSms(recipient.getPhoneNumber().orElseThrow(IllegalArgumentException::new), message);
    }

    @Override
    public Recipient setCommunication(Recipient recipient, String value) {
        recipient.setPhoneNumber(value);
        return recipient;
    }

    @Override
    public Communication getType() {
        return Communication.SMS;
    }
}
