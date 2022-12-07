package com.override.controller;

import com.override.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VkController {

    @Autowired
    private VkService vkService;

    Thread thread = new VkService();


    @GetMapping("/securityCode/{login}")
    public String getSecurityCode(@PathVariable("login") String login){
        return vkService.generateCode(login);
    }

    @GetMapping("/enableVkBot")
    public void getVkCode() {
        thread.start();
    }

    @GetMapping("/vkChatId/{login}")
    public String getVkChatId(@PathVariable("login") String login){
        return vkService.getVkChatId(login);
    }
}
