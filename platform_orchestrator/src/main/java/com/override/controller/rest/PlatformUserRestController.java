package com.override.controller.rest;

import com.override.models.enums.Role;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/platformUser")
public class PlatformUserRestController {

    @Autowired
    private PlatformUserService platformUserService;

    @GetMapping("/role")
    public Role checkPlatformUserRole(HttpServletRequest request) {
        return platformUserService.checkPlatformUserRole(request);
    }
}
