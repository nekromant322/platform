package com.override.service;

import com.override.model.PlatformUser;
import com.override.repository.PlatformUserRepository;
import enums.StudyStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomStudentDetailServiceTest {

    @InjectMocks
    CustomStudentDetailService customStudentDetailService;

    @Mock
    PlatformUserRepository platformUserRepository;

    @Test
    void testLoadUserByUsername() {
        CustomStudentDetailService.CustomStudentDetails customStudentDetailsTest = new CustomStudentDetailService.CustomStudentDetails(generateTestUser());

        when(platformUserRepository.findFirstByLogin(generateTestUser().getLogin())).thenReturn(generateTestUser());

        UserDetails userDetails = customStudentDetailService.loadUserByUsername(generateTestUser().getLogin());

        assertEquals(customStudentDetailsTest.getAuthorities(), userDetails.getAuthorities());
        assertEquals(customStudentDetailsTest.getPassword(), userDetails.getPassword());
        assertEquals(customStudentDetailsTest.getUsername(), userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsernameWhenStatusBAN() {
        PlatformUser platformUserBan = generateTestUser();
        platformUserBan.setStudyStatus(StudyStatus.BAN);

        CustomStudentDetailService.CustomStudentDetails customStudentDetailsTest = new CustomStudentDetailService.CustomStudentDetails(generateTestUser());

        when(platformUserRepository.findFirstByLogin(generateTestUser().getLogin())).thenReturn(platformUserBan);

        assertThrows(RuntimeException.class, () -> customStudentDetailService.loadUserByUsername(generateTestUser().getLogin()));

    }
}