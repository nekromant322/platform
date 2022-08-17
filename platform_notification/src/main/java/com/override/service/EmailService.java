package com.override.service;

import dto.MailDTO;

public interface EmailService {

    void sendSimpleMail(MailDTO mailDTO);
}
