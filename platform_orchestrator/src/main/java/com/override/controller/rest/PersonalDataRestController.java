package com.override.controller.rest;

import com.override.model.PersonalData;
import com.override.model.RequestPersonalData;
import com.override.service.PersonalDataService;
import com.override.service.RequestInNotificationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(
            value = "Сохраняет данные в БД",
            notes = "Сохраняет персональные данные в БД, либо сохраняет данные в таблицу запросов для проверки админом" +
                    "Также сохраняет данные в таблицу получателей в Нотификатор")
    public void patch(@RequestBody PersonalData personalData,
                      @ApiParam(
                              name = "userLogin",
                              type = "String",
                              value = "Логин пользователя",
                              example = "Shu",
                              required = true)
                      @PathVariable String userLogin) {
        personalDataService.saveOrSendToCheck(personalData, userLogin);
        requestInNotificationService.saveRecipient(personalData, userLogin);
    }

    @GetMapping("/requestToCheck/{userLogin}")
    @ApiOperation(
            value = "Поиск запроса на изменение",
            notes = "Поиск запроса (измененная копия модели PersonalData) на изменнеие в таблице для запросов")
    public RequestPersonalData request(
            @ApiParam(
            name = "userLogin",
            type = "String",
            value = "Логин пользователя",
            example = "Shu",
            required = true)
            @PathVariable("userLogin") String userLogin) {
        return personalDataService.findRequestPersonalDataByLogin(userLogin);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/requestToCheck/{userLogin}")
    @ApiOperation(
            value = "Сохраняет и удаляет данные в БД",
            notes = "Сохраняет данные в БД с персональными данными и удаляет копию этих данных из таблицы запросов")
    public void saveUsersPersonalData(@RequestBody PersonalData personalData,
                                      @ApiParam(
                                              name = "userLogin",
                                              type = "String",
                                              value = "Логин пользователя",
                                              example = "Shu",
                                              required = true)
                                      @PathVariable String userLogin) {
        personalDataService.save(personalData, userLogin);
        personalDataService.deleteRequestToCheck(personalData);
    }
}
