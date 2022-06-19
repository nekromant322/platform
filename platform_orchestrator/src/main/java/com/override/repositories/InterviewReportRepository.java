package com.override.repositories;

import com.override.models.InterviewReport;
import com.override.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewReportRepository extends JpaRepository<InterviewReport, Long> {

    List<InterviewReport> findAllByUserLoginAndStatus(String userLogin, Status status);

    List<InterviewReport> findAllByStatus(Status status);
}
