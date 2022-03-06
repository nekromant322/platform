package com.override.controller.rest;

import com.override.models.CodeTry;
import com.override.service.StudentCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codeView")
public class CodeViewRestController {

    @Autowired
    private StudentCodeService studentCodeService;

    @GetMapping
    public List<CodeTry> studentCodes(@PathVariable CodeTry codeTry){
        return studentCodeService.findAllStudentCodes(codeTry.getUser());
    }
}
