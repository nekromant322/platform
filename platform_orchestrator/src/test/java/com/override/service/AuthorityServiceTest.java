package com.override.service;

import com.override.model.Authority;
import com.override.model.enums.Role;
import com.override.repository.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthorityServiceTest {

    @InjectMocks
    private AuthorityService authorityService;

    @Mock
    private AuthorityRepository authorityRepository;

    @Test
    public void testGetAuthorityByRole() {
        Authority authority = new Authority(null, "ADMIN");

        when(authorityRepository.findByAuthority(Role.ADMIN.getName())).thenReturn(Optional.of(authority));

        authorityService.getAuthorityByRole(Role.ADMIN);

        verify(authorityRepository, times(1)).findByAuthority(Role.ADMIN.getName());
    }

    @Test
    public void testSave() {
        String roleName = "USER";

        authorityService.save(roleName);

        verify(authorityRepository, times(1)).save(new Authority(null, roleName));
    }

    @Test
    public void testCheckIfTableIsEmpty() {
        when(authorityRepository.countAll()).thenReturn(0);

        assertEquals(authorityService.checkIfTableIsEmpty(), true);
    }
}