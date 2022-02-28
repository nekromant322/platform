package com.override.controller.rest;

import com.override.feigns.CodeExecutorFeign;
import dtos.CodeTryDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void getCodeTryResult(@RequestBody @Valid CodeTryDTO codeTryDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Введенный код некорректен (не прошел по памяти)");
        }
        codeExecutorFeign.execute(codeTryDTO);
    }
}
