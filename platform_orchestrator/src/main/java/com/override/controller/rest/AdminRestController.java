package com.override.controller.rest;

import com.override.model.PlatformUser;
import com.override.model.enums.Role;
import enums.StudyStatus;
import com.override.service.PlatformUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class AdminRestController {

    private final PlatformUserService userService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/promote-student/{id}/{role}")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @PathVariable Role role) {
        return userService.updateUserRole(id, role);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/getAllStudents")
    public List<PlatformUser> getAllStudents() {
        return userService.getAllStudents();
    }
}