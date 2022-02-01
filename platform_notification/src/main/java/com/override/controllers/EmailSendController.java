package com.override.controllers;

import com.override.service.EmailService;
import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/email/send")
@RestController
public class EmailSendController {

    @Autowired
    EmailService emailService;

    @PostMapping("/mailDTO")
    public void sendTextFromMailDTO(@RequestBody MailDTO mailDTO) {
        emailService.sendSimpleMail(mailDTO);
    }

    @PostMapping("/test")
    public void test() {
        emailService.sendSimpleMail("free2fqn@gmail.com", "Test", "Test message");
    }

}
