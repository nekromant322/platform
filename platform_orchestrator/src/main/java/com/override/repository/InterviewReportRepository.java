package com.override.repository;

import com.override.model.InterviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewReportRepository extends JpaRepository<InterviewReport, Long> {

    List<InterviewReport> findAllByUserLoginAndStatus(String userLogin, Status status);

    List<InterviewReport> findAllByStatus(Status status);
}
