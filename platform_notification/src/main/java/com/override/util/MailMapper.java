package com.override.util;

import dtos.MailDTO;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailMapper {
    public SimpleMailMessage dtoToSimpleMail(MailDTO mailDTO) {
        SimpleMailMessage resultMail = new SimpleMailMessage();
        resultMail.setFrom(mailDTO.getFrom());
        resultMail.setTo(mailDTO.getTo().toArray(new String[mailDTO.getTo().size()]));
        resultMail.setBcc(mailDTO.getBcc().toArray(new String[mailDTO.getBcc().size()]));
        resultMail.setCc(mailDTO.getCc().toArray(new String[mailDTO.getCc().size()]));
        resultMail.setReplyTo(mailDTO.getReplyTo());
        resultMail.setSentDate(mailDTO.getSentDate());
        resultMail.setSubject(mailDTO.getSubject());
        resultMail.setText(mailDTO.getText());
        return resultMail;
    }
}
