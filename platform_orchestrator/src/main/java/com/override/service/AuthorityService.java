package com.override.service;

import com.override.model.Authority;
import com.override.model.enums.Role;
import com.override.repository.AuthorityRepository;
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

    public boolean checkIfTableIsEmpty(){
        return  authorityRepository.countAll() == 0;
    }
}