package com.override.controller.rest;

import com.override.model.StudentReport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @PostMapping
    public void postReport(@RequestBody StudentReport report) {
        System.out.println(report);
    }
}
