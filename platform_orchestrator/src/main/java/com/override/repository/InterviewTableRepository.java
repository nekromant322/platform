package com.override.repository;

import com.override.model.InterviewTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewTableRepository extends JpaRepository<InterviewTable, Long> {
}
