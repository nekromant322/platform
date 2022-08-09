package com.override.controller.rest;

import com.override.service.ActGenerationService;
import com.override.service.CustomStudentDetailService;
import com.override.service.PlatformUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/act")
public class ActGenerationRestController {

    @Autowired
    private ActGenerationService actGenerationService;

    @Autowired
    private PlatformUserService platformUserService;

    @Secured("ROLE_ADMIN")
    @GetMapping("{studentLogin}")
    @ApiOperation(value = "Создает акт формата .PDF для админа. Где именно используется на платформе, я не нашел(")
    public void generateAct(@PathVariable String studentLogin) {
        actGenerationService.createPDF(platformUserService.findPlatformUserByLogin(studentLogin).getPersonalData());
    }

    @GetMapping
    @ApiOperation(value = "Создает акт формата .PDF для юзера. Где именно используется на платформе, я не нашел(")
    public void generateCurrentUserAct(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        actGenerationService.createPDF(platformUserService.findPlatformUserByLogin(user.getUsername()).getPersonalData());
    }
}
