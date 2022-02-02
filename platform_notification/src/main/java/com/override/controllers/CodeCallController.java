package com.override.controllers;

import com.override.service.CodeCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeCallController {

    private final CodeCallService codeCallService;

    @Autowired
    CodeCallController(CodeCallService codeCallService) {
        this.codeCallService = codeCallService;
    }

    @PostMapping("/codecall")
    public void callToClient(@RequestBody String clientPhoneNumber) {
        codeCallService.sendGet(clientPhoneNumber);
    }
}
