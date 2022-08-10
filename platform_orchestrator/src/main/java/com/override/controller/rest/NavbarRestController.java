package com.override.controller.rest;

import com.override.model.domain.NavbarElement;
import com.override.service.NavbarService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Возвращает список \"навбар элементов\". Либо для админа, либо для юзера, либо для выпусника, " +
            "смотря кто заходит на платформу.")
    public List<NavbarElement> getNavbar(HttpServletRequest request) {
        return navbarService.getNavbar(request);
    }
}
