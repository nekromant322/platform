package com.override.controller.rest;

import com.override.service.PlatformUserService;
import enums.StudyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final PlatformUserService userService;

    @Secured("ROLE_ADMIN")
    @PutMapping("/work-student/{id}/{status}")
    public ResponseEntity<String> updateWorkStatus(@PathVariable Long id, @PathVariable String status) {
        return userService.updateStatus(id, StudyStatus.valueOf(status));
    }
}
