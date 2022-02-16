package com.override.controller;


import com.github.benmanes.caffeine.cache.Cache;
import dtos.HelpMeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class HelpMeRestController {

    private final Cache<Integer, HelpMeDTO> helpMeDTOCache;

    @Autowired
    public HelpMeRestController(Cache<Integer, HelpMeDTO> helpMeDTOCache) {
        this.helpMeDTOCache = helpMeDTOCache;
    }

    @PostMapping("/helpMe")
    int postHelpMeDTO(@RequestBody HelpMeDTO helpMeDTO) {
        helpMeDTOCache.put(helpMeDTO.hashCode(), helpMeDTO);
        log.info("Added to cache, key value {}", helpMeDTO.hashCode());
        return helpMeDTO.hashCode();
    }

    @GetMapping("/helpMe/cache/{key}")
    HelpMeDTO getHelpMeCachedDTO(@PathVariable int key) {
        log.info("Sended key {}", key);
        return helpMeDTOCache.getIfPresent(key); // здесь может выскочить null, обработку null завещаю фронту. А может и все ок
    }
}
