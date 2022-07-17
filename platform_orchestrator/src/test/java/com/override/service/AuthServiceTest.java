package com.override.service;

import com.override.config.security.JwtProvider;
import com.override.exception.AuthException;
import com.override.model.PlatformUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private CustomStudentDetailService studentDetailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLoginWhenUserExist() {
        String token = "token";
        PlatformUser user = generateTestUser();
        UserDetails userDetails = new CustomStudentDetailService.CustomStudentDetails(user);

        when(studentDetailService.loadUserByUsername(user.getLogin())).thenReturn(userDetails);
        when(jwtProvider.generateToken(userDetails.getUsername(), userDetails.getAuthorities())).thenReturn(token);
        when(passwordEncoder.matches(user.getPassword(), userDetails.getPassword())).thenReturn(true);


        String loginToken = authService.login(user.getLogin(), user.getPassword());

        assertEquals(token, loginToken);
        verify(studentDetailService, times(1)).loadUserByUsername(user.getLogin());
        verify(jwtProvider, times(1)).generateToken(userDetails.getUsername(), userDetails.getAuthorities());
    }

    @Test
    public void testLoginWhenUserNotExist() {
        PlatformUser user = generateTestUser();

        when(studentDetailService.loadUserByUsername(user.getLogin())).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> authService.login(user.getLogin(), user.getPassword()));
        verify(studentDetailService, times(1)).loadUserByUsername(user.getLogin());
    }

    @Test
    public void testLoginWhenPasswordDifferent() {
        PlatformUser user = generateTestUser();
        UserDetails userDetails = new CustomStudentDetailService.CustomStudentDetails(user);

        when(studentDetailService.loadUserByUsername(user.getLogin())).thenReturn(userDetails);
        when(passwordEncoder.matches(user.getPassword(), userDetails.getPassword())).thenReturn(false);

        assertThrows(AuthException.class, () -> authService.login(user.getLogin(), user.getPassword()));
        verify(studentDetailService, times(1)).loadUserByUsername(user.getLogin());
    }
}