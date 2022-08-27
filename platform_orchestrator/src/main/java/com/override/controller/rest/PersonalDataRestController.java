package com.override.controller.rest;

import com.override.model.PersonalData;
import com.override.model.RequestPersonalData;
import com.override.service.PersonalDataService;
import com.override.service.RequestInNotificationService;
import dto.PersonalDataDTO;
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
            value = "Сохранение данных в БД",
            notes = "Сохранение персональных данных в таблицу с персональными данными, " +
                    "либо в таблицу запросов на изменение персональных данных в зависимости от контекста," +
                    "а также сохранение данных в таблицу получателей в Нотификатор")
    public void patch(@RequestBody PersonalDataDTO personalDataDTO,
                      @ApiParam(value = "Логин пользователя", example = "Shu") @PathVariable String userLogin) {
        personalDataService.saveOrCreateRequest(personalDataDTO, userLogin);
        requestInNotificationService.saveRecipient(personalDataDTO, userLogin);
    }

    @GetMapping("/requestToCheck/{userLogin}")
    @ApiOperation(
            value = "Поиск запроса на изменение",
            notes = "Поиск запроса на изменение в таблице запросов на изменение персональных данных")
    public RequestPersonalData request(
            @ApiParam(value = "Логин пользователя", example = "Shu") @PathVariable String userLogin) {
        return personalDataService.findRequestPersonalDataByLogin(userLogin);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/requestToCheck/{userLogin}")
    @ApiOperation(
            value = "Согласование запроса на изменние персональных данных",
            notes = "Сохранение данных в таблице с персональными данными " +
                    "и удаление данных из таблицы запросов на изменение персональных данных")
    public void saveUsersPersonalData(@RequestBody PersonalDataDTO personalDataDTO,
                                      @ApiParam(value = "Логин пользователя", example = "Shu")
                                      @PathVariable String userLogin) {
        personalDataService.save(personalDataDTO, userLogin);
        personalDataService.deleteRequestToCheck(personalDataDTO);
    }
}
