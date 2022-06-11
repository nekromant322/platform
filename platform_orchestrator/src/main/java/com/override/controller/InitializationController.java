package com.override.controller;

import com.override.util.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("init")
public class InitializationController {

    @Autowired
    InitializationService initializationService;

    @GetMapping
    public String init(){
        initializationService.initTestData();
        return "redirect:http://localhost:8000/";
    }
}
