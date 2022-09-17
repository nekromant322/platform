package com.override.controller;

import com.override.service.SmsRuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calls")
public class SmsRuController {

    @Autowired
    private SmsRuService smsRuService;

    @PostMapping("/code")
    public String callToClient(@RequestBody String clientPhoneNumber) {
        return smsRuService.verifyNumber(clientPhoneNumber);
    }

    @GetMapping("/balance")
    public double checkBalance() {
        return smsRuService.getBalance();
    }
}
