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

    @GetMapping("/codeTryList")
    public String codeTryListPage() {
        return "codeTryList";
    }

    @GetMapping("/helpMe/{key}")
    public String getHelpView(@PathVariable int key) {
        if (helpMeCache.getIfPresent(key) == null) {
            return "emptyHelpMe";
        } else {
            return "helpMe";
        }
    }

    @GetMapping("/statistics")
    public String statisticPage(){
        return "statistics";
    }

    @GetMapping("/report")
    public String reportPage() {
        return "report";
    }

    @GetMapping("/login")
    public String authPage() {
        return "login";
    }
}
