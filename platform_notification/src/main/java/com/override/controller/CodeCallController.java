package com.override.controller;

import com.override.service.CodeCallService;
import dtos.CodeCallSecurityCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calls")
public class CodeCallController {

    @Autowired
    private CodeCallService codeCallService;


    @GetMapping("/code")
    public CodeCallSecurityCodeDTO callToClient(@RequestBody String clientPhoneNumber) {
        String securityCode = codeCallService.verifyNumber(clientPhoneNumber);
        return new CodeCallSecurityCodeDTO(securityCode);
    }

    @GetMapping("/balance")
    public double checkBalance() {
        return codeCallService.getBalance();
    }
}
