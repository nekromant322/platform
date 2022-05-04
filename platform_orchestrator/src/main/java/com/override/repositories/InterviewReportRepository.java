package com.override.repositories;

import com.override.models.InterviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewReportRepository extends JpaRepository<InterviewReport, Long> {
}
