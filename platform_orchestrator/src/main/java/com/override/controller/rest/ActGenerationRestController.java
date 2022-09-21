package com.override.controller.rest;

import com.override.service.ActGenerationService;
import com.override.service.CustomStudentDetailService;
import com.override.service.PlatformUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/act")
public class ActGenerationRestController {

    @Autowired
    private ActGenerationService actGenerationService;

    @Autowired
    private PlatformUserService platformUserService;

    @Secured("ROLE_ADMIN")
    @GetMapping("{studentLogin}")
    @ApiOperation(value = "Создает акт о выполненной работе формата .PDF для админа")
    public void generateAct(@PathVariable String studentLogin) {
        actGenerationService.createPDF(platformUserService.findPlatformUserByLogin(studentLogin).getPersonalData());
    }

    /*@GetMapping
    @ApiOperation(value = "Создает акт о выполненной работе формата .PDF для текущего юзера")
    public ResponseEntity<Resource> generateCurrentUserAct(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) throws IOException {
        actGenerationService.createPDF(platformUserService.findPlatformUserByLogin(user.getUsername()).getPersonalData());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getName())
                .body(new ByteArrayResource(document.getContent()));
    }*/
}
