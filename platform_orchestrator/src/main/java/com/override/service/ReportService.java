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
        if (true) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Уже есть репорт на эту дату");
        }
        return new ResponseEntity<>("Запрос принят\n" + report.toString(), HttpStatus.OK);
    }
}