package com.override.controller.rest;

import com.override.models.PlatformUser;
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
    @PostMapping("/promote-student/{id}")
    public ResponseEntity<String> updateToAdmin(@PathVariable Long id) {
        return userService.updateToAdmin(id);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/getAllStudents")
    public List<PlatformUser> getAllStudents() {
        return userService.getAllStudents();
    }
}