package com.override.controller;

import com.override.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VkController {

    @Autowired
    private VkService vkService;

    @GetMapping("/{login}/securityCode")
    public ResponseEntity<String> getSecurityCode(@PathVariable("login") String login) {
        return new ResponseEntity<>(vkService.generateCode(login), HttpStatus.OK);
    }

    @GetMapping("/vkChatId/{login}")
    public ResponseEntity<Integer> getVkChatId(@PathVariable("login") String login) {
        return new ResponseEntity<>(vkService.getVkChatId(login), HttpStatus.OK);
    }
}
