package com.override.controller.rest;

import com.override.model.StudentReport;
import com.override.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.override.service.CustomStudentDetailService.CustomStudentDetails;

@RestController
@RequestMapping("/report")
public class ReportRestController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<String> postReport(@RequestBody StudentReport report, @AuthenticationPrincipal CustomStudentDetails user) {
        return reportService.saveReport(report, user.getUsername());
    }
}
