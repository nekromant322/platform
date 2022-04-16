package com.override.controller;

import com.override.service.RecipientService;
import dtos.RecipientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @PostMapping("/saveRecipients")
    void saveRecipients(@RequestBody RecipientDTO recipientDTO) {
        recipientService.save(recipientDTO);
    }
}
