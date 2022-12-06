package com.override.controller;

import com.override.mapper.RecipientMapper;
import com.override.service.RecipientService;
import com.override.service.VkService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import dto.RecipientDTO;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @Autowired
    private RecipientMapper recipientMapper;

    @GetMapping("/recipient/{login}")
    RecipientDTO getRecipient(@PathVariable("login") String login) {
        return recipientMapper.entityToDto(recipientService.findRecipientByLogin(login));
    }

    @PostMapping("/recipients/save")
    void saveRecipient(@RequestBody RecipientDTO recipientDTO) {
        recipientService.save(recipientDTO);
    }

    @PostMapping("/recipients/setCommunication")
    void setCommunication(
            @RequestParam("login") String login,
            @RequestParam("value") String value,
            @RequestParam("type") Communication type) {
        recipientService.updateCommunication(login, value, type);
    }

    @PostMapping("/recipients/delete")
    void deleteRecipient(@RequestBody RecipientDTO recipientDTO) {
        recipientService.delete(recipientDTO);
    }
}
