package com.override.controller;

import com.github.benmanes.caffeine.cache.Cache;
import dtos.HelpMeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @Autowired
    private Cache<Integer, HelpMeDTO> helpMeCache;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/courses")
    public String coursesPage() {
        return "courses";
    }

    @GetMapping("/balancePage")
    public String balancePage() {
        return "balanceCheck";
    }

    @GetMapping("/helpMe/{key}")
    public String getHelpView(@PathVariable int key) {
        if (helpMeCache.getIfPresent(key) == null) {
            return "emptyHelpMe";
        } else {
            return "helpMe";
        }
    }
}
