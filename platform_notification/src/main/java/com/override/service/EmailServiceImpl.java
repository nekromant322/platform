package com.override.service;

import com.override.util.EmailMapper;
import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    public EmailMapper emailMapper;

    @Override
    public void sendSimpleMail(MailDTO mailDTO) {
        List<SimpleMailMessage> messageList = emailMapper.dtoToListOfSimpleMails(mailDTO);
        for (SimpleMailMessage message : messageList) {
            emailSender.send(message);
        }
    }
}
