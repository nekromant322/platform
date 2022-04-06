package com.override.controller.rest;

import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import com.override.service.CustomStudentDetailService;
import com.override.service.PersonalDataService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personalData")
public class PersonalDataRestController {

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private PersonalDataService personalDataService;

    @GetMapping("/current")
    public PlatformUser getUserInfo(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.findPlatformUserByLogin(user.getUsername());
    }

    @PatchMapping("{userLogin}")
    public void patch(@RequestBody PersonalData personalData,
                      @PathVariable String userLogin) {
        personalDataService.save(personalData, userLogin);
    }
}
