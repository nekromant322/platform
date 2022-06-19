package com.override.repositories;

import com.override.model.InterviewReport;
import com.override.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository  extends JpaRepository<InterviewReport, Long> {

    List<InterviewReport> findAllByUserLoginAndStatus(String userLogin, Status status);

    List<InterviewReport> findAllByStatus(Status status);
}
