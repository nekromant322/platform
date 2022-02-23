package com.override.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin/get")
    public String getAdmin() {
        return "Hi admin";
    }
}