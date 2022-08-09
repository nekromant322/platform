package com.override.controller.rest;

import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.model.enums.Role;
import com.override.service.CustomStudentDetailService;
import com.override.service.PlatformUserService;
import enums.StudyStatus;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Возвращает роль пользователя")
    public Role getPlatformUserRole(HttpServletRequest request) {
        return platformUserService.getPlatformUserRole(request);
    }

    @GetMapping("/current")
    @ApiOperation(value = "Возвращает PlatformUser из platformUserRepository текущего пользователя")
    public PlatformUser findPlatformUser(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.findPlatformUserByLogin(user.getUsername());
    }


    @GetMapping("/{login}")
    @ApiOperation(value = "Возвращает PlatformUser из platformUserRepository по логину")
    public PlatformUser findPlatformUserByLogin(@PathVariable String login) {
        return platformUserService.findPlatformUserByLogin(login);
    }

    @GetMapping("/coursePart")
    @ApiOperation(value = "Возвращает CoursePart из platformUserRepository текущего пользователя")
    public CoursePart getCoursePart(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.getCurrentCoursePart(user.getUsername());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    @ApiOperation(value = "Возвращает List<PlatformUser> (список всех PlatformUser, кроме админов)")
    public List<PlatformUser> getAllStudents() {
        return platformUserService.getAllStudents();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/{role}")
    @ApiOperation(value = "Обновляет List<Authority> юзера (PlatformUser) и сохраняет в platformUserRepository")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @PathVariable Role role) {
        return platformUserService.updateUserRole(id, role);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "Обновляет CoursePart юзера (PlatformUser) и сохраняет в platformUserRepository")
    @PostMapping("/promoteCoursePart/{id}/{coursePart}")
    public ResponseEntity<String> updateCoursePart(@PathVariable Long id, @PathVariable CoursePart coursePart) {
        return platformUserService.updateCurrentCoursePart(id, coursePart);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/{status}")
    @ApiOperation(value = "Обновляет StudyStatus юзера (PlatformUser) и сохраняет в platformUserRepository")
    public ResponseEntity<String> updateWorkStatus(@PathVariable Long id, @PathVariable String status) {
        return platformUserService.updateStatus(id, StudyStatus.valueOf(status));
    }


}