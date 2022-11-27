package com.override.controller.rest;

import com.override.feign.CodeExecutorFeign;
import dto.StepikCodeTryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stepikCodeTry")
public class StepikCodeTryController {

    @Autowired
    private CodeExecutorFeign codeExecutorFeign;

    @PostMapping
    public ResponseEntity<String> sendStepikCodeTry(@RequestBody StepikCodeTryDTO stepikCodeTryDTO) {
        String result = codeExecutorFeign.executeStepikCode(stepikCodeTryDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
