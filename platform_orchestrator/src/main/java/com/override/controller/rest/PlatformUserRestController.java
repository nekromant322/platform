package com.override.controller.rest;

import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.service.CustomStudentDetailService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/platformUser")
public class PlatformUserRestController {

    @Autowired
    private PlatformUserService platformUserService;

    @GetMapping("/role")
    public Role getPlatformUserRole(HttpServletRequest request) {
        return platformUserService.getPlatformUserRole(request);
    }

    @GetMapping("/current")
    public PlatformUser findPlatformUser(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.findPlatformUserByLogin(user.getUsername());
    }
}
