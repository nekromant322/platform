package com.override.service;

import com.override.util.MailMapper;
import dtos.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    public MailMapper mailMapper;

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
        SimpleMailMessage resultSimpleMailMessage = mailMapper.dtoToSimpleMail(mailDTO);
        emailSender.send(resultSimpleMailMessage);
    }
}
