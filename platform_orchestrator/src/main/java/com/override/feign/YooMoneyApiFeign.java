package com.override.feign;

import dto.YooMoneyConfirmationRequestDTO;
import dto.YooMoneyConfirmationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "YooMoneyApiFeign", url = "${yooMoney.url}")
public interface YooMoneyApiFeign {

    @PostMapping(value = "/payments", consumes = "application/json", produces = "application/json")
    YooMoneyConfirmationResponseDTO getConfirmationResponse(
            @RequestHeader(value = "Idempotence-Key", required = true) Integer random,
            @RequestHeader(value = "Authorization", required = true) String auth,
            @RequestBody YooMoneyConfirmationRequestDTO yooMoneyConfirmationRequestDTO);

    @GetMapping(value = "/payments/{paymentId}", consumes = "application/json")
    YooMoneyConfirmationResponseDTO getPaymentInfo(
            @PathVariable("paymentId") String id,
            @RequestHeader(value = "Authorization", required = true) String auth);
}
