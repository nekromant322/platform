package com.override.controller.rest;

import com.override.model.RequestPersonalData;
import com.override.service.PersonalDataService;
import dto.PersonalDataDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personalData")
public class PersonalDataRestController {

    @Autowired
    private PersonalDataService personalDataService;

    @PatchMapping("{userLogin}")
    @Operation(summary = "Сохранение данных в БД",
            description = "Сохранение персональных данных в таблицу с персональными данными, " +
                    "либо в таблицу запросов на изменение персональных данных в зависимости от контекста," +
                    "а также сохранение данных в таблицу получателей в Нотификатор")
    public void patch(@RequestBody PersonalDataDTO personalDataDTO,
                      @Parameter(description = "Логин пользователя", example = "Shu") @PathVariable String userLogin) {
        personalDataService.saveOrCreateRequest(personalDataDTO, userLogin);
    }

    @GetMapping("/requestToCheck/{userLogin}")
    @Operation(summary = "Поиск запроса на изменение",
            description = "Поиск запроса на изменение в таблице запросов на изменение персональных данных")
    public RequestPersonalData request(
            @Parameter(description = "Логин пользователя", example = "Shu") @PathVariable String userLogin) {
        return personalDataService.findRequestPersonalDataByLogin(userLogin);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/requestToCheck/{userLogin}")
    @Operation(summary = "Согласование запроса на изменние персональных данных",
            description = "Сохранение данных в таблице с персональными данными " +
                    "и удаление данных из таблицы запросов на изменение персональных данных")
    public void saveUsersPersonalData(@RequestBody PersonalDataDTO personalDataDTO,
                                      @Parameter(description = "Логин пользователя", example = "Shu")
                                      @PathVariable String userLogin) {
        personalDataService.save(personalDataDTO, userLogin);
        personalDataService.deleteRequestToCheck(personalDataDTO);
    }
}
