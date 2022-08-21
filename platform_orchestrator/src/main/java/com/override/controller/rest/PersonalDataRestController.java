package com.override.controller.rest;

import com.override.model.PersonalData;
import com.override.model.RequestPersonalData;
import com.override.service.PersonalDataService;
import com.override.service.RequestInNotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personalData")
public class PersonalDataRestController {

    @Autowired
    private PersonalDataService personalDataService;

    @Autowired
    private RequestInNotificationService requestInNotificationService;

    @PatchMapping("{userLogin}")
    @ApiOperation(value = "Сохраняет персональные данные в БД, а так же сохраняет данные в таблицу получателей в Нотификатор")
    public void patch(@RequestBody PersonalData personalData,
                      @PathVariable String userLogin) {
        personalDataService.saveOrSendToCheck(personalData, userLogin);
        requestInNotificationService.saveRecipient(personalData, userLogin);
    }

    @GetMapping("/requestToCheck/{userLogin}")
    public RequestPersonalData request(@PathVariable("userLogin") String userLogin) {
        return personalDataService.findRequestPersonalDataByLogin(userLogin);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/requestToCheck/{userLogin}")
    public void saveUsersPersonalData(@RequestBody PersonalData personalData,
                        @PathVariable String userLogin) {
        personalDataService.save(personalData, userLogin);
        personalDataService.deleteRequestToCheck(personalData);
    }

}
