package com.override.controllers;

import com.override.service.MailService;
import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/email/resend")
@RestController
public class EmailResendController {

    @Autowired
    MailService mailService;

    @PostMapping("/mailDTO")
    public void autoResendTextFromMailDTO(@RequestBody MailDTO mailDTO) {
        mailService.sendSimpleMail(mailDTO);
    }

    @PostMapping("/manually/{mail}")
    public void manuallyResendTextTest(@PathVariable String mailTo) {
        mailService.sendSimpleMail(mailTo, "Test", "Testing platform");
    }

    @PostMapping("/test")
    public void test() {
        mailService.sendSimpleMail("free2fqn@gmail.com", "Test", "Test message");
    }

}
