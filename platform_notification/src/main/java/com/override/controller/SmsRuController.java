package com.override.controller;

import com.override.service.SmsRuService;
import dtos.CodeCallSecurityCodeDTO;
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

    @GetMapping("/sms")
    public void sendSms(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("message") String message) {
        smsRuService.sendSms(phoneNumber, message);
    }
}
