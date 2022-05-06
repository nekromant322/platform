package com.override.service;

import com.override.exception.UserAlreadyExistException;
import com.override.models.Authority;
import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import com.override.models.UserSettings;
import com.override.models.enums.Role;
import com.override.repositories.PlatformUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlatformUserServiceTest {

    @InjectMocks
    private PlatformUserService platformUserService;

    @Mock
    private PlatformUserRepository platformUserRepository;

    @Mock
    private PasswordGeneratorService passwordGeneratorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private HttpServletRequest request;

    private final Authority adminAuthority = new Authority();
    private final Authority userAuthority = new Authority();

    @Test
    public void testUpdateUserToAdmin() {
        PlatformUser student = new PlatformUser();
        student.setAuthorities(new ArrayList<>() {{
            add(userAuthority);
        }});

        when(platformUserRepository.findById(1L)).thenReturn(Optional.of(student));
        when(authorityService.getAuthorityByRole(Role.ADMIN)).thenReturn(adminAuthority);

        ResponseEntity<String> entity = platformUserService.updateToAdmin(1L);

        assertEquals(student.getAuthorities(), new ArrayList<>() {{
            add(userAuthority);
            add(adminAuthority);
        }});
        verify(platformUserRepository, times(1)).save(student);
        assertEquals(new ResponseEntity<>("OK", HttpStatus.OK), entity);
    }

    @Test
    public void testUpdateUserToAdminWhenUserNotExist() {
        when(platformUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> platformUserService.updateToAdmin(1L));
        verify(platformUserRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllStudents() {
        List<PlatformUser> platformUsers = new ArrayList<>() {{
            add(new PlatformUser());
            add(new PlatformUser());
        }};

        when(authorityService.getAuthorityByRole(Role.ADMIN)).thenReturn(adminAuthority);
        when(platformUserRepository.findByAuthoritiesNotContaining(adminAuthority)).thenReturn(platformUsers);

        List<PlatformUser> allStudents = platformUserService.getAllStudents();

        assertEquals(platformUsers, allStudents);
        verify(platformUserRepository, times(1)).findByAuthoritiesNotContaining(adminAuthority);
    }

    @Test
    public void testGetUserByLogin() {
        String login = "login";
        PlatformUser notNullUser = new PlatformUser();

        when(platformUserRepository.findFirstByLogin(login)).thenReturn(notNullUser);

        PlatformUser student = platformUserService.findPlatformUserByLogin(login);

        assertEquals(notNullUser, student);
        verify(platformUserRepository, times(1)).findFirstByLogin(login);
    }

    @Test
    public void testGenerateUser() {
        String login = "login";
        String password = "password";
        PlatformUser user = new PlatformUser(
                null,
                login,
                password,
                new ArrayList<>() {{
                    add(userAuthority);
                }},
                new PersonalData(),
                new UserSettings()
        );

        when(authorityService.getAuthorityByRole(Role.USER)).thenReturn(userAuthority);
        when(passwordGeneratorService.generateStrongPassword()).thenReturn(password);
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(platformUserRepository.findFirstByLogin(login)).thenReturn(null);

        PlatformUser student = platformUserService.generateAccount(login);

        assertEquals(student, user);
        verify(platformUserRepository, times(1)).save(user);
    }

    @Test
    public void testGenerateUserWhenLoginExist() {
        String login = "login";
        String chatId = "chatId";
        PlatformUser notNullUser = new PlatformUser();

        when(platformUserRepository.findFirstByLogin(login)).thenReturn(notNullUser);

        assertThrows(UserAlreadyExistException.class, () -> platformUserService.generateAccount(login));
        verify(platformUserRepository, times(1)).findFirstByLogin(login);
        verify(platformUserRepository, times(0)).save(any());
    }

    @Test
    public void testGetUser() {
        PlatformUser notNullUser = new PlatformUser();
        String login = "login";

        when(platformUserRepository.findFirstByLogin(login)).thenReturn(notNullUser);

        PlatformUser accountByChatId = platformUserService.findPlatformUserByLogin(login);

        assertEquals(accountByChatId, notNullUser);
        verify(platformUserRepository, times(1)).findFirstByLogin(login);
    }

    @Test
    public void testSaveUser() {
        String login = "login";
        PlatformUser notNullUser = new PlatformUser();
        notNullUser.setLogin(login);
        notNullUser.setPersonalData(new PersonalData());
        notNullUser.setUserSettings(new UserSettings());

        when(platformUserRepository.findFirstByLogin(login)).thenReturn(null);
        when(platformUserRepository.save(notNullUser)).thenReturn(notNullUser);

        PlatformUser user = platformUserService.save(notNullUser);

        assertEquals(user, notNullUser);
        verify(platformUserRepository, times(1)).save(notNullUser);
    }

    @Test
    public void testSaveUserWhenLoginExist() {
        String login = "login";
        PlatformUser notNullUser = new PlatformUser();
        notNullUser.setLogin(login);

        when(platformUserRepository.findFirstByLogin(login)).thenReturn(notNullUser);

        assertThrows(UserAlreadyExistException.class, () -> platformUserService.save(notNullUser));
        verify(platformUserRepository, times(0)).save(notNullUser);
    }

    @Test
    void checkPlatformUserRoleAdmin() {
        when(request.isUserInRole("ROLE_ADMIN")).thenReturn(true);

        Role admin = platformUserService.checkPlatformUserRole(request);

        assertEquals(admin, Role.ADMIN);
    }

    @Test
    void checkPlatformUserRoleUser() {
        when(request.isUserInRole("ROLE_ADMIN")).thenReturn(false);

        Role user = platformUserService.checkPlatformUserRole(request);

        assertEquals(user, Role.USER);
    }
}