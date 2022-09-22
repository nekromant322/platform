package com.override.repository;

import com.override.model.PlatformUser;
import com.override.model.StudentReport;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StudentReportRepository extends CrudRepository<StudentReport, Long> {

    StudentReport findFirstByDateAndStudentLogin(LocalDate date, String login);

    Optional<StudentReport> findFirstByStudentOrderByDateDesc(PlatformUser platformUser);
}