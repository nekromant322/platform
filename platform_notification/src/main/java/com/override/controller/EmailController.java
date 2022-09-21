package com.override.controller;

import com.override.service.EmailServiceImpl;
import dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping
    void sendMessageByMail(@RequestBody MailDTO mailDTO) {
        emailService.sendSimpleMail(mailDTO);
    }
}
