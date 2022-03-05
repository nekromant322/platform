package com.override.controller.rest;

import com.override.models.StudentCode;
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
    public List<StudentCode> studentCodes(@PathVariable StudentCode studentCode){
        return studentCodeService.findAllStudentCodes(studentCode.getUser());
    }
}
