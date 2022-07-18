package com.override.controller.rest;

import com.override.feign.NotificatorFeign;
import com.override.model.PersonalData;
import com.override.service.PersonalDataService;
import dto.RecipientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personalData")
public class PersonalDataRestController {

    @Autowired
    private PersonalDataService personalDataService;

    @Autowired
    private NotificatorFeign notificatorFeign;

    @PatchMapping("{userLogin}")
    public void patch(@RequestBody PersonalData personalData,
                      @PathVariable String userLogin) {
        personalDataService.save(personalData, userLogin);

        String phoneNumber;

        if (personalData.getPhoneNumber() == null) {
            phoneNumber = "";
        } else {
            phoneNumber = Long.toString(personalData.getPhoneNumber());
        }

        RecipientDTO recipientDTO = RecipientDTO.builder()
                .login(userLogin)
                .email(personalData.getEmail())
                .phoneNumber(phoneNumber)
                .build();

        notificatorFeign.saveRecipient(recipientDTO);
    }
}
