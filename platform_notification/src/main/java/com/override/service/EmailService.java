package com.override.service;

import dtos.MailDTO;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public interface EmailService {

    public void sendSimpleMail(String toAdress, String subject, String message);

    public void sendSimpleMail(MailDTO mailDTO);

//    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException;
}
