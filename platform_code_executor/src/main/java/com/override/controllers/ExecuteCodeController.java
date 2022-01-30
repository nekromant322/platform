package com.override.controllers;

import com.override.service.ExecuteCodeService;
import dtos.CodeTryDTO;
import dtos.TestResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExecuteCodeController {

    private final ExecuteCodeService executeCodeService;

    @PostMapping("/execute")
    public TestResultDTO execute(@RequestBody CodeTryDTO codeTryDTO) {

        return executeCodeService.runCode(codeTryDTO.getTaskIdentifier(), codeTryDTO.getStudentsCode());
    }
}
