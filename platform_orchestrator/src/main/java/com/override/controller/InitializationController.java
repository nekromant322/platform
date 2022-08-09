package com.override.controller;

import com.override.util.InitializationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("dev")
@RequestMapping("init")
public class InitializationController {

    @Autowired
    private InitializationService initializationService;

    @GetMapping
    @ApiOperation(value = "Создает тестовые данные для платформы")
    public String init(){
        initializationService.initTestData();
        return "redirect:/";
    }
}
