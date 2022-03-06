package com.override.controller.rest;

import com.override.feigns.CodeExecutorFeign;
import com.override.service.StudentCodeService;
import dtos.CodeTryDTO;
import dtos.TestResultDTO;
import enums.CodeExecutionStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import static com.override.service.CustomStudentDetailService.CustomStudentDetails;

@RestController
@RequestMapping("codeTry")
public class CodeTryRestController {

    @Autowired
    StudentCodeService studentCodeService;
    @Autowired
    private CodeExecutorFeign codeExecutorFeign;

    @PostMapping
    public ResponseEntity<String> getCodeTryResult(@RequestBody @Valid CodeTryDTO codeTryDTO, BindingResult result,
                                                   @AuthenticationPrincipal CustomStudentDetails user) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Введенный код некорректен (не прошел по длине)", HttpStatus.BAD_REQUEST);
        }
        TestResultDTO testResult = codeExecutorFeign.execute(codeTryDTO);
        studentCodeService.saveCodeTry(codeTryDTO, testResult, user.getUsername());
        if (testResult.getCodeExecutionStatus() == CodeExecutionStatus.OK) {
            return new ResponseEntity<>("Все тесты пройдены", HttpStatus.OK);
        }
        return new ResponseEntity<>(testResult.getOutput(), HttpStatus.BAD_REQUEST);
    }
}
