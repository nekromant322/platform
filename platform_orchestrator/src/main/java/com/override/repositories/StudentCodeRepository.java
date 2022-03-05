package com.override.repositories;

import com.override.models.PlatformUser;
import com.override.models.StudentCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCodeRepository extends JpaRepository<StudentCode, Long> {
    List<StudentCode> findAllByUser(PlatformUser user);
}
