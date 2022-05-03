package com.override.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/balancePage")
    public String balancePage() {
        return "balanceCheck";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/allStudents")
    public String allStudentsPage() {
        return "allStudents";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/joinRequests")
    public String joinRequestsPage() {
        return "joinRequests";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/statistics")
    public String statisticPage(){
        return "statistics";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/questionsAdmin")
    public String questionsAdminPage() {
        return "questionsAdmin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/reviewAdmin")
    public String reviewAdminPage() {
        return "reviewAdmin";
    }
}
