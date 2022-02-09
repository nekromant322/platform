package com.override.controller;

import com.override.feigns.CodeExecutorFeign;
import dtos.CodeTryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("codeTry")
public class CodeTryController {

    @Autowired
    private CodeExecutorFeign codeExecutorFeign;

    @PostMapping
    public void getCodeTryResult(@RequestBody CodeTryDTO codeTryDTO) {
        codeExecutorFeign.execute(codeTryDTO);
    }
}
