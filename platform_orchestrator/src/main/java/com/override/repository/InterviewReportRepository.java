package com.override.repository;

import com.override.model.InterviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewReportRepository extends JpaRepository<InterviewReport, Long> {
}
