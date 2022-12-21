package com.override.controller.rest;

import com.override.service.YooMoneyService;
import dto.YooMoneyConfirmationResponseDTO;
import dto.YooMoneyRequestInfoDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class YooMoneyRestController {

    @Autowired
    private YooMoneyService yooMoneyService;

    @PostMapping("/yooMoney/authorize")
    @ApiOperation(value = "Запрашивает и возвращает confirmation token для оплаты через Юкассу")
    public YooMoneyConfirmationResponseDTO getConfirmationToken(@RequestBody YooMoneyRequestInfoDTO infoDTO) {
        return yooMoneyService.getConfirmationResponseDTO(infoDTO);
    }
}
