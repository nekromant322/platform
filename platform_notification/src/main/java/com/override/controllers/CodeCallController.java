package com.override.controllers;

import com.override.service.CodeCallService;
import dtos.CodeCallSecurityCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeCallController {

    private final CodeCallService codeCallService;

    @Autowired
    CodeCallController(CodeCallService codeCallService) {
        this.codeCallService = codeCallService;
    }

    @GetMapping("/codecall")
    public CodeCallSecurityCodeDTO callToClient(@RequestBody String clientPhoneNumber) {
        String securityCode = codeCallService.sendGet(clientPhoneNumber);
        return new CodeCallSecurityCodeDTO(securityCode);
    }
}
