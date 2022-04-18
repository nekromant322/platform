package com.override.service;

import dtos.MailDTO;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<String> sendSimpleMail(MailDTO mailDTO);

}
