package com.override.controller.rest;

import com.override.model.domain.NavbarElement;
import com.override.service.NavbarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class NavbarRestController {

    @Autowired
    private NavbarService navbarService;

    @GetMapping("/navbar")
    public List<NavbarElement> getNavbar(HttpServletRequest request) {
        return navbarService.getNavbar(request);
    }
}
