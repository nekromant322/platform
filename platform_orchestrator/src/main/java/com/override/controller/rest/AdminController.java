package com.override.controller.rest;

import com.override.models.PlatformUser;
import com.override.service.PlatformUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

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