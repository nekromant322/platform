package com.override.controller;

import com.override.service.ExecuteCodeService;
import com.override.service.StepikService;
import dto.CodeTryDTO;
import dto.TestResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecuteCodeController {

    @Autowired
    private ExecuteCodeService executeCodeService;

    @Autowired
    private StepikService stepikService;

    @PostMapping("/execute")
    @Operation(summary = "Возвращает результат проверки кода задачи")
    public TestResultDTO execute(@RequestBody CodeTryDTO codeTryDTO) {
        if (codeTryDTO.getAttempt() != null) {
            return stepikService.runStepikCode(codeTryDTO);
        }
        return executeCodeService.runCode(codeTryDTO.getTaskIdentifier(), codeTryDTO.getStudentsCode());
    }
}

