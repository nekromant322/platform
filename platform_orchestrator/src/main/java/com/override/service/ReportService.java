package com.override.service;

import com.override.models.StudentReport;
import com.override.repositories.StudentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    @Autowired
    private StudentReportRepository reportRepository;

    public ResponseEntity<String> saveReport(StudentReport report, String studentLogin) {
        if (reportRepository.findFirstByDateAndStudentLogin(report.getDate(), studentLogin) != null) {
            return new ResponseEntity<>("Уже есть отчет на эту дату", HttpStatus.CONFLICT);
        }
        report.setStudentLogin(studentLogin);
        reportRepository.save(report);
        return new ResponseEntity<>("Отчет принят\n" + report.toString(), HttpStatus.OK);
    }
}