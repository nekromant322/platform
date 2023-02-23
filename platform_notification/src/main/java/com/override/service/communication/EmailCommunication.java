package com.override.service.communication;

import com.override.model.Recipient;
import com.override.service.EmailService;
import dto.MailDTO;
import enums.CommunicationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
                .to(Collections.singletonList(recipient.getEmail().orElseThrow(() -> new IllegalStateException("У пользователя " +
                        recipient.getLogin() + " не найден email"))))
                .text(message)
                .subject(mailSubject)
                .build());
    }

    @Override
    public Recipient updateRecipient(Recipient recipient, String value) {
        recipient.setEmail(value);
        return recipient;
    }

    @Override
    public CommunicationType getType() {
        return CommunicationType.EMAIL;
    }
}
