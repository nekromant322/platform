package com.override.controller;

import com.override.service.ExecuteCodeService;
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

    @PostMapping("/execute")
    public TestResultDTO execute(@RequestBody CodeTryDTO codeTryDTO) {
        return executeCodeService.runCode(codeTryDTO.getTaskIdentifier(), codeTryDTO.getStudentsCode());
    }
}
