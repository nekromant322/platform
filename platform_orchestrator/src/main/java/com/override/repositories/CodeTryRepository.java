package com.override.repositories;

import com.override.models.PlatformUser;
import com.override.models.CodeTry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeTryRepository extends JpaRepository<CodeTry, Long> {
    List<CodeTry> findAllByUser(PlatformUser user);
}
