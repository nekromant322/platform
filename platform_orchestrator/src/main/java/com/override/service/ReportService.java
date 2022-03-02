package com.override.service;

import com.override.models.StudentReport;
import com.override.repositories.StudentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final StudentReportRepository reportDAO;

    public ResponseEntity saveReport(StudentReport report) {
        if (false) {
            return new ResponseEntity<>("Уже есть отчет на эту дату", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Отчет принят\n" + report.toString(), HttpStatus.OK);
    }
}