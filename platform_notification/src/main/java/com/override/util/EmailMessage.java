package com.override.util;

import com.override.models.Recipient;
import com.override.service.EmailService;
import dtos.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
public class EmailMessage implements MessageStrategy {

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<HttpStatus> sendMessage(Recipient recipient, String message) {
        return emailService.sendSimpleMail(MailDTO.builder()
                .to(Collections.singletonList(recipient.getEmail()))
                .text(message)
                .subject("test")
                .build());
    }
}
