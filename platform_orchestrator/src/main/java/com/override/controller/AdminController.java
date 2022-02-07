package com.override.controller;

import com.override.feigns.NotificatorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
public class AdminController {

    NotificatorFeign notificatorFeign;

    @Autowired
    public AdminController(NotificatorFeign notificatorFeign) {
        this.notificatorFeign = notificatorFeign;
    }

    @GetMapping("/balance")
    public String balancePage(Model model) {
        model.addAttribute("balance", notificatorFeign.getBalance());
        return "balance-check";
    }

//    @RequestMapping
//    @GetMapping("/ping")
//    public String ping() {
//        return "ping";
//    }
}
