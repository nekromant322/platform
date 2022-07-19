package com.override.controller.rest;

import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.model.enums.Role;
import com.override.service.CustomStudentDetailService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{login}")
    public PlatformUser findPlatformUserByLogin(@PathVariable String login) {
        return platformUserService.findPlatformUserByLogin(login);
    }

    @GetMapping("/getCoursePart")
    public CoursePart getCoursePart(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.getCurrentCoursePart(user.getUsername());
    }
}