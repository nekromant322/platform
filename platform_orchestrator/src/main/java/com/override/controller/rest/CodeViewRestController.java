package com.override.controller.rest;

import com.override.models.CodeTry;
import com.override.service.CodeTryService;
import com.override.service.CustomStudentDetailService.CustomStudentDetails;
import dtos.TaskIdentifierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codeTryRest")
public class CodeViewRestController {

    @Autowired
    private CodeTryService codeTryService;

    @GetMapping("/all")
    public List<CodeTry> studentCodes(@AuthenticationPrincipal CustomStudentDetails user) {
        return codeTryService.findAllCodes(user.getUsername());
    }

    @GetMapping("/lesson")
    public List<CodeTry> studentCodesLesson(@AuthenticationPrincipal CustomStudentDetails user,
                                            @RequestBody TaskIdentifierDTO taskIdentifierDTO) {
        return codeTryService.findAllByLesson(user.getUsername(), taskIdentifierDTO.getChapter(),
                taskIdentifierDTO.getStep(),
                taskIdentifierDTO.getLesson());
    }
}
