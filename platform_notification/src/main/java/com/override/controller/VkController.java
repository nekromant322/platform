package com.override.controller;

import com.override.service.VkService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VkController {

    @Autowired
    private VkService vkService;


    @GetMapping("/securityCode/{login}")
    public String getSecurityCode(@PathVariable("login") String login){
        return vkService.getCode(login);
    }

    @GetMapping("/vkChatId/{login}")
    public Integer getVkCode(@PathVariable String login) throws ClientException, InterruptedException, ApiException {
        return vkService.getVkChatId(login);
    }
}
