package com.override.repository;

import com.override.model.InterviewData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewDataRepository extends JpaRepository<InterviewData, Long> {
}
