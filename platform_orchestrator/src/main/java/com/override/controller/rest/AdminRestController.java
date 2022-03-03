package com.override.controller.rest;

import com.override.models.PlatformUser;
import com.override.service.PlatformUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {

    private final PlatformUserService userService;

    @PostMapping("/promote-student/{id}")
    public ResponseEntity<String> updateToAdmin(@PathVariable Long id) {
        return userService.updateToAdmin(id);
    }

    @GetMapping("/getAllStudents")
    public List<PlatformUser> getAllStudents() {
        return userService.getAllStudents();
    }
}