package com.override.util;

import dtos.MailDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

//TODO сейчас "From" достается из переменной-окружения. Но зачем-то же её можно регулировать, как настроить это?
@Component
public class EmailMapper {
    public SimpleMailMessage dtoToSimpleMail(MailDTO mailDTO) {
        SimpleMailMessage resultMail = new SimpleMailMessage();
        resultMail.setFrom(System.getenv("MAIL_USER_NAME"));
        resultMail.setTo(mailDTO.getTo().toArray(new String[mailDTO.getTo().size()]));
        if (mailDTO.getBcc() != null) {
            resultMail.setBcc(mailDTO.getBcc().toArray(new String[mailDTO.getBcc().size()]));
        }
        if (mailDTO.getCc() != null) {
            resultMail.setCc(mailDTO.getCc().toArray(new String[mailDTO.getCc().size()]));
        }
        if (mailDTO.getReplyTo() != null) {
            resultMail.setReplyTo(mailDTO.getReplyTo());
        }
        resultMail.setSentDate(mailDTO.getSentDate());
        resultMail.setSubject(mailDTO.getSubject());
        resultMail.setText(mailDTO.getText());
        return resultMail;
    }
}
