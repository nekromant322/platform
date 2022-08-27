package com.override.service;

import com.override.feign.NotificatorFeign;
import dto.PersonalDataDTO;
import dto.RecipientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestInNotificationService {

    @Autowired
    private NotificatorFeign notificatorFeign;

    public void saveRecipient(PersonalDataDTO personalDataDTO, String userLogin) {

        RecipientDTO recipientDTO = RecipientDTO.builder()
                .login(userLogin)
                .email(personalDataDTO.getEmail())
                .phoneNumber(personalDataDTO.getPhoneNumber() != null ? Long.toString(personalDataDTO.getPhoneNumber()) : null)
                .build();

        notificatorFeign.saveRecipient(recipientDTO);
    }
}
