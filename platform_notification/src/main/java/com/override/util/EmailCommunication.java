package com.override.util;

import com.override.models.Recipient;
import com.override.service.EmailService;
import dtos.MailDTO;
import enums.Communication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class EmailCommunication implements CommunicationStrategy {

    @Value("${mail.subject}")
    private String mailSubject;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendMessage(Recipient recipient, String message) {
        emailService.sendSimpleMail(MailDTO.builder()
                .to(Collections.singletonList(recipient.getEmail().orElseThrow(IllegalArgumentException::new)))
                .text(message)
                .subject(mailSubject)
                .build());
    }

    @Override
    public Recipient setCommunication(Recipient recipient, String value) {
        recipient.setEmail(value);
        return recipient;
    }

    @Override
    public Communication getType() {
        return Communication.EMAIL;
    }
}
