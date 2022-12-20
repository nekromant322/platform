package com.override.controller.rest;

import com.override.feign.YooMoneyApiFeign;
import com.override.service.YooMoneyService;
import dto.YooMoneyConfirmationRequestDTO;
import dto.YooMoneyConfirmationResponseDTO;
import dto.YooMoneyRequestInfoDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class YooMoneyRestController {

    @Value("${yooMoney.authorization}")
    private String authorization;

    @Autowired
    private YooMoneyApiFeign yooMoneyApiFeign;

    @Autowired
    private YooMoneyService yooMoneyService;

    @PostMapping("/confirmationToken")
    @ApiOperation(value = "Запрашивает и возвращает confirmation token для оплаты через Юкассу")
    public YooMoneyConfirmationResponseDTO getConfirmationToken(@RequestBody YooMoneyRequestInfoDTO yooMoneyRequestInfoDTO) {
        YooMoneyConfirmationRequestDTO request = yooMoneyService.createYooMoneyConfirmationRequestDTO(yooMoneyRequestInfoDTO);
        return yooMoneyApiFeign.getConfirmationResponse(yooMoneyService.getRandomInteger(), authorization, request);
    }
}
