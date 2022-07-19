package com.override.controller.rest;

import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.model.enums.Role;
import com.override.service.PlatformUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Secured("ROLE_ADMIN")
    @PostMapping("/promoteCoursePart/{id}/{coursePart}")
    public ResponseEntity<String> updateCoursePart(@PathVariable Long id, @PathVariable CoursePart coursePart) {
        return userService.updateCurrentCoursePart(id, coursePart);
    }
}