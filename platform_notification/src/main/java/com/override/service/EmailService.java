package com.override.service;

import dtos.MailDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    HttpStatus sendSimpleMail(MailDTO mailDTO);

}
