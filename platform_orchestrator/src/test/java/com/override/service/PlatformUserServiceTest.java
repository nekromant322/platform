package com.override.service;

import com.override.exception.UserAlreadyExistException;
import com.override.models.Authority;
import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.repositories.PlatformUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlatformUserServiceTest {

    @InjectMocks
    private PlatformUserService userService;

    @Mock
    private PlatformUserRepository accountRepository;

    @Mock
    private PasswordGeneratorService passwordGeneratorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthorityService authorityService;

    private final Authority adminAuthority = new Authority();
    private final Authority userAuthority = new Authority();

    @Test
    public void testUpdateUserToAdmin() {
        PlatformUser student = new PlatformUser();
        student.setAuthorities(new ArrayList<>() {{
            add(userAuthority);
        }});

        when(accountRepository.findById(1L)).thenReturn(Optional.of(student));
        when(authorityService.getAuthorityByRole(Role.ADMIN)).thenReturn(adminAuthority);

        ResponseEntity<String> entity = userService.updateToAdmin(1L);

        assertEquals(student.getAuthorities(), new ArrayList<>() {{
            add(userAuthority);
            add(adminAuthority);
        }});
        verify(accountRepository, times(1)).save(student);
        assertEquals(new ResponseEntity<>("OK", HttpStatus.OK), entity);
    }

    @Test
    public void testUpdateUserToAdminWhenUserNotExist() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.updateToAdmin(1L));
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllStudents() {
        List<PlatformUser> platformUsers = new ArrayList<>() {{
            add(new PlatformUser());
            add(new PlatformUser());
        }};

        when(authorityService.getAuthorityByRole(Role.ADMIN)).thenReturn(adminAuthority);
        when(accountRepository.findByAuthoritiesNotContaining(adminAuthority)).thenReturn(platformUsers);

        List<PlatformUser> allStudents = userService.getAllStudents();

        assertEquals(platformUsers, allStudents);
        verify(accountRepository, times(1)).findByAuthoritiesNotContaining(adminAuthority);
    }

    @Test
    public void testGetUserByLogin() {
        String login = "login";
        PlatformUser notNullUser = new PlatformUser();

        when(accountRepository.findFirstByLogin(login)).thenReturn(notNullUser);

        PlatformUser student = userService.findPlatformUserByLogin(login);

        assertEquals(notNullUser, student);
        verify(accountRepository, times(1)).findFirstByLogin(login);
    }

    @Test
    public void testGenerateUser() {
        String login = "login";
        String chatId = "chatId";
        String password = "password";
        PlatformUser user = new PlatformUser(
                null,
                login,
                password,
                chatId,
                new ArrayList<>() {{
                    add(userAuthority);
                }},
                new PersonalData()
        );

        when(authorityService.getAuthorityByRole(Role.USER)).thenReturn(userAuthority);
        when(passwordGeneratorService.generateStrongPassword()).thenReturn(password);
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(accountRepository.findFirstByLogin(login)).thenReturn(null);

        PlatformUser student = userService.generateAccount(login, chatId);

        assertEquals(student, user);
        verify(accountRepository, times(1)).save(user);
    }

    @Test
    public void testGenerateUserWhenLoginExist() {
        String login = "login";
        String chatId = "chatId";
        PlatformUser notNullUser = new PlatformUser();

        when(accountRepository.findFirstByLogin(login)).thenReturn(notNullUser);

        assertThrows(UserAlreadyExistException.class, () -> userService.generateAccount(login, chatId));
        verify(accountRepository, times(1)).findFirstByLogin(login);
        verify(accountRepository, times(0)).save(any());
    }

    @Test
    public void testGetUser() {
        PlatformUser notNullUser = new PlatformUser();
        String chatId = "chatId";

        when(accountRepository.findFirstByTelegramChatId(chatId)).thenReturn(notNullUser);

        PlatformUser accountByChatId = userService.getAccountByChatId(chatId);

        assertEquals(accountByChatId, notNullUser);
        verify(accountRepository, times(1)).findFirstByTelegramChatId(chatId);
    }

    @Test
    public void testSaveUser() {
        String login = "login";
        PlatformUser notNullUser = new PlatformUser();
        notNullUser.setLogin(login);
        notNullUser.setPersonalData(new PersonalData());

        when(accountRepository.findFirstByLogin(login)).thenReturn(null);
        when(accountRepository.save(notNullUser)).thenReturn(notNullUser);

        PlatformUser user = userService.save(notNullUser);

        assertEquals(user, notNullUser);
        verify(accountRepository, times(1)).save(notNullUser);
    }

    @Test
    public void testSaveUserWhenLoginExist() {
        String login = "login";
        PlatformUser notNullUser = new PlatformUser();
        notNullUser.setLogin(login);

        when(accountRepository.findFirstByLogin(login)).thenReturn(notNullUser);

        assertThrows(UserAlreadyExistException.class, () -> userService.save(notNullUser));
        verify(accountRepository, times(0)).save(notNullUser);
    }
}