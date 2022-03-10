package com.override.service;

import com.override.models.Authority;
import com.override.models.enums.Role;
import com.override.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority getAuthorityByRole(Role role) {
        return authorityRepository.findByAuthority(role.getName()).get();
    }

    public Authority save(String roleName) {
        return authorityRepository.save(new Authority(null, roleName));
    }
}