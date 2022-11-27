package com.override.controller;

import com.override.feign.StepikCodeExecutorFeign;
import com.override.service.ExecuteCodeService;
import com.override.service.StepikService;
import dto.CodeTryDTO;
import dto.StepikCodeTryDTO;
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
    private StepikCodeExecutorFeign stepikCodeExecutorFeign;

    @Autowired
    private StepikService stepikService;

    @PostMapping("/execute")
    public TestResultDTO execute(@RequestBody CodeTryDTO codeTryDTO) {
        return executeCodeService.runCode(codeTryDTO.getTaskIdentifier(), codeTryDTO.getStudentsCode());
    }

    @PostMapping("/executeStepikCode")
    public String stepikCodeExecute(@RequestBody StepikCodeTryDTO stepikCodeTryDTO) {
        String token = stepikService.getStepikToken();
        stepikCodeExecutorFeign.execute(token, stepikService.getRequest(stepikCodeTryDTO));
        String result = stepikCodeExecutorFeign.getResult(stepikCodeTryDTO.getStep(), token);
        while (result.contains("evaluation")) {
            result = stepikCodeExecutorFeign.getResult(stepikCodeTryDTO.getStep(), token);
        }
        return result;
    }
}

