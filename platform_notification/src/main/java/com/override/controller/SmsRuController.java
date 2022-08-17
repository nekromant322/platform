package com.override.controller;

import com.override.service.SmsRuService;
import dto.CodeCallSecurityCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calls")
public class SmsRuController {

    @Autowired
    private SmsRuService smsRuService;

    @GetMapping("/code")
    public CodeCallSecurityCodeDTO callToClient(@RequestBody String clientPhoneNumber) {
        String securityCode = smsRuService.verifyNumber(clientPhoneNumber);
        return new CodeCallSecurityCodeDTO(securityCode);
    }

    @GetMapping("/balance")
    public double checkBalance() {
        return smsRuService.getBalance();
    }
}
