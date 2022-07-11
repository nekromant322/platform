package com.override.repository;

import com.override.model.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Optional<Authority> findByAuthority(String authority);

    @Query("select count(a) from Authority a")
    Integer countAll();
}