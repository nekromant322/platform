package com.override.controller.rest;

import com.override.service.RestoreService;
import dto.ChangePasswordDTO;
import dto.PlatformUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestoreController {

    @Autowired
    private RestoreService restoreService;

    @RequestMapping(value = "/restore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChangePasswordDTO> restore(@RequestBody ChangePasswordDTO changePasswordDTO) {
        if (restoreService.getCodeTelegram(changePasswordDTO.getCode(), changePasswordDTO.getUsername())) {
            restoreService.changePassword(changePasswordDTO);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getCode/{username}")
    public ResponseEntity<PlatformUserDTO> getCode(@PathVariable("username") String username) {
        restoreService.getCodeTelegramSecurity(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
