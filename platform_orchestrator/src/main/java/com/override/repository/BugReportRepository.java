package com.override.repository;

import com.override.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugReportRepository extends JpaRepository<Bug,Long> {
    List<Bug> findAllByUserId(Long id);

}
