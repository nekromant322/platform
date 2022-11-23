package com.override.controller.rest;

import com.override.feign.StepikCodeExecutorFeign;
import com.override.service.StepikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stepikCodeTry")
public class StepikCodeTryController {

    private final String REQUESTS_JSON = "{\"submission\":{\"eta\":null,\"has_session\":false,\"hint\":null,\"reply\":{\"code\":%s,\"language\":\"java8\"},\"reply_url\":null,\"score\":null,\"session_id\":null,\"status\":null,\"time\":\"2022-11-22T17:07:38.090Z\",\"attempt\":\"759223701\",\"session\":null}}";

    @Autowired
    private StepikCodeExecutorFeign stepikCodeExecutorFeign;

    @Autowired
    private StepikService stepikService;

    @PostMapping
    public void sendStepikCodeTry(@RequestBody String code) {
        stepikCodeExecutorFeign.execute(stepikService.getStepikToken(), String.format(REQUESTS_JSON, code));
    }

    @GetMapping(value = "/result", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> getStepikCodeTryResult() {
        String result = stepikCodeExecutorFeign.getResult("57792", stepikService.getStepikToken());
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
