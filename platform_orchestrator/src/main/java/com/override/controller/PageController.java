package com.override.controller;

import com.override.feigns.NotificatorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    NotificatorFeign notificatorFeign;

    @Autowired
    public PageController(NotificatorFeign notificatorFeign) {
        this.notificatorFeign = notificatorFeign;
    }

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/courses")
    public String coursesPage() {
        return "courses";
    }

    @ResponseBody
    @GetMapping("/admin/rest/balance")
    public String getBalance() {
        return notificatorFeign.getBalance();
    }

    @GetMapping("admin/balancePage")
    public String balancePage() {
        return "balance-check";
    }

}
