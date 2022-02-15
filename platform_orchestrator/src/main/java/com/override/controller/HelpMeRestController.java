package com.override.controller;


import com.github.benmanes.caffeine.cache.Cache;
import dtos.HelpMeTaskTextAndCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpMeRestController {

    private final Cache<Integer, HelpMeTaskTextAndCodeDTO> helpMeDTOCache;

    @Autowired
    public HelpMeRestController(Cache<Integer, HelpMeTaskTextAndCodeDTO> helpMeDTOCache) {
        this.helpMeDTOCache = helpMeDTOCache;
    }

    @PostMapping("/helpMe")
    int postHelpMeDTO(@RequestBody HelpMeTaskTextAndCodeDTO helpMeDTO) {
        helpMeDTOCache.put(helpMeDTO.hashCode(), helpMeDTO);
        System.out.println("KEY: " + helpMeDTO.hashCode() + " " + helpMeDTOCache.getIfPresent(helpMeDTO.hashCode()));
        return helpMeDTO.hashCode();
    }
}
