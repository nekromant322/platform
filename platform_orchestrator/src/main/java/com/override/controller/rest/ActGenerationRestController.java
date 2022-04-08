package com.override.controller.rest;

import com.override.service.ActGenerationService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actGeneration")
public class ActGenerationRestController {

    @Autowired
    private ActGenerationService actGenerationService;

    @Autowired
    private PlatformUserService platformUserService;

    @Secured("ROLE_ADMIN")
    @GetMapping("{studentLogin}")
    public void generateAct(@PathVariable String studentLogin) {
        actGenerationService.createPDF(platformUserService.findPlatformUserByLogin(studentLogin).getPersonalData());
    }


}
