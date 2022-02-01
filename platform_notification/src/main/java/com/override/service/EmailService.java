package com.override.service;

import dtos.MailDTO;

public interface EmailService {

    void sendSimpleMail(MailDTO mailDTO);

}
