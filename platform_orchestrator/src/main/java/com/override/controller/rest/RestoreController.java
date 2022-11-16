package com.override.controller.rest;

import com.override.service.RestoreService;
import dto.ChangePasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restore")
public class RestoreController {

    @Autowired
    private RestoreService restoreService;

    @PostMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public void restore(@RequestBody ChangePasswordDTO changePasswordDTO) {
        if (restoreService.isEqualCodeTelegram(changePasswordDTO.getCode(), changePasswordDTO.getUsername())) {
            restoreService.changePassword(changePasswordDTO);
        }
    }

    @GetMapping("/getCode/{username}")
    public void getCode(@PathVariable("username") String username) {
        restoreService.sendSecurityCode(username);
    }
}
