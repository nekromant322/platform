package com.override.repositories;

import com.override.models.StudentReport;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface StudentReportRepository extends CrudRepository<StudentReport, Long> {

    StudentReport findFirstByDateAndStudentLogin(LocalDate date, String login);
}