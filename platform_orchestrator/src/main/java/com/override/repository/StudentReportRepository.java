package com.override.repository;

import com.override.model.PlatformUser;
import com.override.model.StudentReport;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface StudentReportRepository extends CrudRepository<StudentReport, Long> {

    StudentReport findFirstByDateAndStudentLogin(LocalDate date, String login);

    StudentReport findFirstByStudentOrderByDateDesc(PlatformUser platformUser);
}