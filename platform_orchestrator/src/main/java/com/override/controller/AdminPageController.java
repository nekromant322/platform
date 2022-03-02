package com.override.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/balancePage")
    public String balancePage() {
        return "balanceCheck";
    }

    @GetMapping("/allStudents")
    public String allStudentsPage() {
        return "allStudents";
    }
}