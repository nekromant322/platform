package com.override.controller.rest;

import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.model.enums.Role;
import com.override.service.CustomStudentDetailService;
import com.override.service.PlatformUserService;
import enums.StudyStatus;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/platformUsers")
public class PlatformUserRestController {

    @Autowired
    private PlatformUserService platformUserService;

    @GetMapping("/role")
    @Operation(summary = "Возвращает роль пользователя")
    public Role getPlatformUserRole(HttpServletRequest request) {
        return platformUserService.getPlatformUserRole(request);
    }

    @GetMapping("/current")
    @Operation(summary = "Возвращает \"платформ юзера\" из БД для текущего пользователя")
    public PlatformUser findPlatformUser(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.findPlatformUserByLogin(user.getUsername());
    }

    @GetMapping("/{login}")
    @Operation(summary = "Возвращает \"платформ юзера\" из БД по логину")
    public PlatformUser findPlatformUserByLogin(@PathVariable String login) {
        return platformUserService.findPlatformUserByLogin(login);
    }

    @GetMapping("/coursePart")
    @Operation(summary = "Возвращает \"часть курса\" из БД текущего пользователя")
    public CoursePart getCoursePart(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.getCurrentCoursePart(user.getUsername());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    @Operation(summary = "Возвращает список всех \"платформ юзеров\", кроме админов)")
    public List<PlatformUser> getAllStudents() {
        return platformUserService.getAllStudents();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/{role}")
    @Operation(summary = "Обновляет роль \"платформ юзера\" и сохраняет в БД")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @PathVariable Role role) {
        return platformUserService.updateUserRole(id, role);
    }

    @Secured("ROLE_ADMIN")
    @Operation(summary = "Обновляет \"часть курса\" \"платформ юзера\" и сохраняет в БД")
    @PostMapping("/promoteCoursePart/{id}/{coursePart}")
    public ResponseEntity<String> updateCoursePart(@PathVariable Long id, @PathVariable CoursePart coursePart) {
        return platformUserService.updateCurrentCoursePart(id, coursePart);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/{status}")
    @Operation(summary = "Обновляет \"статус обучения\" \"платформ юзера\" и сохраняет в БД")
    public ResponseEntity<String> updateWorkStatus(@PathVariable Long id, @PathVariable String status) {
        return platformUserService.updateStatus(id, StudyStatus.valueOf(status));
    }
}