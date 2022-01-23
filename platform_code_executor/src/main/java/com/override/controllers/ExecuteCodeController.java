package com.override.controllers;

import com.override.service.ExecuteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExecuteCodeController {

    private final ExecuteCodeService executeCodeService;

    @PostMapping("/execute")
    public String execute() {

        return "feign test";
    }
}
