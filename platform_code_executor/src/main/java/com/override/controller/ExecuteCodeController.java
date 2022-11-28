package com.override.controller;

import com.override.service.ExecuteCodeService;
import com.override.service.StepikService;
import dto.CodeTryDTO;
import dto.TestResultDTO;
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
    public TestResultDTO execute(@RequestBody CodeTryDTO codeTryDTO) {
        if (codeTryDTO.getAttempt() != null) {
            return stepikService.runStepikCode(codeTryDTO);
        }
        return executeCodeService.runCode(codeTryDTO.getTaskIdentifier(), codeTryDTO.getStudentsCode());
    }
}

