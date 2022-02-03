package com.override.controller;

import com.override.service.CodeCallService;
import dtos.CodeCallSecurityCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeCallController {

    private final CodeCallService codeCallService;

    @Autowired
    public CodeCallController(CodeCallService codeCallService) {
        this.codeCallService = codeCallService;
    }

    @GetMapping("/codeCall")
    public CodeCallSecurityCodeDTO callToClient(@RequestBody String clientPhoneNumber) {
        String securityCode = codeCallService.verifyNumber(clientPhoneNumber);
        return new CodeCallSecurityCodeDTO(securityCode);
    }

    @GetMapping("/balance")
    public String checkBalance() {
        return codeCallService.getBalance();
    }
}
