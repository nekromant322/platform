package com.override.feign;

import dto.StepikTokenDTO;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "stepik-code-executor", url = "https://stepik.org")
public interface StepikCodeExecutorFeign {

    @PostMapping(value = "/api/submissions", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Отправляет код на проверку StepikAPI")
    void execute(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody String json);

    @GetMapping(value = "/api/submissions?limit=1&order=desc", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Запрашивает результаты проверки на конкретную задачу - numberOfStep")
    String getResult(@RequestParam(value = "step") String numberOfStep, @RequestHeader(value = "Authorization", required = true) String token);

    @PostMapping(value = "/oauth2/token/?grant_type=client_credentials", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Запрашивает токен у StepikAPI по логину и паролю клиента")
    StepikTokenDTO getToken(@RequestHeader(value = "Authorization", required = true) String auth);
}
