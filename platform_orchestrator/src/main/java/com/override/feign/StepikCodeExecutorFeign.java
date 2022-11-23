package com.override.feign;

import dto.StepikTokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "stepik-code-executor", url = "https://stepik.org")
public interface StepikCodeExecutorFeign {

    @PostMapping(value = "/api/submissions", consumes = "application/json", produces = "application/json")
    void execute(@RequestHeader(value = "Authorization", required = true) String token, @RequestBody String json);

    @GetMapping(value = "/api/submissions?limit=1&order=desc", consumes = "application/json", produces = "application/json")
    String getResult(@RequestParam(value = "step") String numberOfStep, @RequestHeader(value = "Authorization", required = true) String token);

    @PostMapping(value = "/oauth2/token/?grant_type=client_credentials", consumes = "application/json", produces = "application/json")
    StepikTokenDTO getToken(@RequestHeader(value = "Authorization", required = true) String auth);
}
