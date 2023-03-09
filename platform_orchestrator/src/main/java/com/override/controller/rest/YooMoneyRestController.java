package com.override.controller.rest;

import com.override.service.YooMoneyService;
import dto.YooMoneyConfirmationResponseDTO;
import dto.YooMoneyRequestInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class YooMoneyRestController {

    @Autowired
    private YooMoneyService yooMoneyService;

    @PostMapping("/yooMoney/authorize")
    @Operation(summary = "Запрашивает и возвращает confirmation token для оплаты через Юкассу")
    public YooMoneyConfirmationResponseDTO getConfirmationToken(@RequestBody YooMoneyRequestInfoDTO infoDTO) {
        return yooMoneyService.getConfirmationResponseDTO(infoDTO);
    }
}
