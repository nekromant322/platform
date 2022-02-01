package com.override.service;

import com.override.util.EmailMapper;
import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    public EmailMapper emailMapper;

    @Override
    public void sendSimpleMail(String toAdress, String subject, String message) {
        SimpleMailMessage resultSimpleMailMessage = new SimpleMailMessage();
        resultSimpleMailMessage.setTo(toAdress);
        resultSimpleMailMessage.setSubject(subject);
        resultSimpleMailMessage.setText(message);
        resultSimpleMailMessage.setFrom(System.getenv("MAIL_USER_NAME"));
        emailSender.send(resultSimpleMailMessage);
    }

    @Override
    public void sendSimpleMail(MailDTO mailDTO) {
        SimpleMailMessage resultSimpleMailMessage = emailMapper.dtoToSimpleMail(mailDTO);
        emailSender.send(resultSimpleMailMessage);
    }
}
