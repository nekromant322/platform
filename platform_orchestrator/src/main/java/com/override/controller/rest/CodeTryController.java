package com.override.controller.rest;

import com.override.feigns.CodeExecutorFeign;
import dtos.CodeTryDTO;
import dtos.TestResultDTO;
import enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("codeTry")
public class CodeTryController {

    @Autowired
    private CodeExecutorFeign codeExecutorFeign;

    @PostMapping
    public ResponseEntity<String> getCodeTryResult(@RequestBody @Valid CodeTryDTO codeTryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Введенный код некорректен (не прошел по длине)", HttpStatus.BAD_REQUEST);
        }
        TestResultDTO testResult = codeExecutorFeign.execute(codeTryDTO);
        if (testResult.getStatus() == Status.OK) {
            return new ResponseEntity<>("Все тесты пройдены", HttpStatus.OK);
        }
        return new ResponseEntity<>(testResult.getOutput(), HttpStatus.BAD_REQUEST);
    }
}
