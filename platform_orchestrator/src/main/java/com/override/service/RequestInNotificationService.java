package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PersonalData;
import dto.RecipientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestInNotificationService {

    @Autowired
    private NotificatorFeign notificatorFeign;

    public void saveRecipient(PersonalData personalData, String userLogin) {

        RecipientDTO recipientDTO = RecipientDTO.builder()
                .login(userLogin)
                .email(personalData.getEmail())
                .phoneNumber(personalData.getPhoneNumber() != null ? Long.toString(personalData.getPhoneNumber()) : null)
                .build();

        notificatorFeign.saveRecipient(recipientDTO);
    }
}
