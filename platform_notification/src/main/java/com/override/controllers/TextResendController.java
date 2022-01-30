package com.override.controllers;

//import dtos.CodeTryDTO;
import com.override.util.MailMapper;
import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//TODO разобраться с mailsender, достать из application.yml логопасс

@RestController
public class TextResendController {

    @Autowired
    MailMapper mailMapper;

    @PostMapping("/resend/email")
    public void resendTextViaEmail(@RequestBody MailDTO mailDTO) {
        MailMessage mailMessage = mailMapper.dtoToSimpleMail(mailDTO);
    }
}
