package com.override.controller;

import com.override.service.EmailService;
import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/email/send")
@RestController
public class EmailSendController {

    @Autowired
    EmailService emailService;

    @PostMapping("/mailDTO")
    public ResponseEntity<String> sendTextFromMailDTO(@RequestBody MailDTO mailDTO) {
        return emailService.sendSimpleMail(mailDTO);
    }

}
