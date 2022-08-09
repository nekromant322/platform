package com.override.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/balancePage")
    @ApiOperation(value = "Возвращает balanceCheck.html")
    public String balancePage() {
        return "balanceCheck";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/allStudents")
    @ApiOperation(value = "Возвращает allStudents.html")
    public String allStudentsPage() {
        return "allStudents";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/joinRequests")
    @ApiOperation(value = "Возвращает joinRequests.html")
    public String joinRequestsPage() {
        return "joinRequests";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/statistics")
    @ApiOperation(value = "Возвращает statistics.html")
    public String statisticPage(){
        return "statistics";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/questionsAdmin")
    @ApiOperation(value = "Возвращает questionsAdmin.html")
    public String questionsAdminPage() {
        return "questionsAdmin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/reviewAdmin")
    @ApiOperation(value = "Возвращает reviewAdmin.html")
    public String reviewAdminPage() {
        return "reviewAdmin";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/allBugs")
    @ApiOperation(value = "Возвращает allBugs.html")
    public  String allBugsPage(){
        return "allBugs";
    }
}
