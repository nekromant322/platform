package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.mapper.BugReportMapper;
import com.override.model.Authority;
import com.override.model.Bug;
import com.override.model.PlatformUser;
import com.override.model.enums.Role;
import com.override.repository.BugReportRepository;
import com.override.repository.PlatformUserRepository;
import dto.BugReportsDTO;
import enums.Communication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BugReportServiceTest {

    @InjectMocks
    private BugReportService bugReportService;

    @Mock
    private BugReportRepository bugReportRepository;

    @Mock
    private PlatformUserService platformUserService;

    @Mock
    private BugReportMapper bugReportMapper;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private PlatformUserRepository platformUserRepository;

    @Mock
    private NotificatorFeign notificatorFeign;

    @Test
    public void uploadFileTest() {
        PlatformUser platformUser = generateTestUser();
        Authority authority = new Authority();
        List<PlatformUser> platformUsers = new ArrayList<>();
        MockMultipartFile file = new MockMultipartFile("bug",
                "Screenshot.png",
                "image/png",
                "some content".getBytes());

        when(platformUserService.findPlatformUserByLogin("Andrey")).thenReturn(platformUser);

        when(authorityService.getAuthorityByRole(Role.ADMIN)).thenReturn(authority);

        when(platformUserRepository.findAll()).thenReturn(platformUsers);

        bugReportService.uploadFile(file, platformUser.getLogin(), "some text");

        notificatorFeign.sendMessage(platformUser.getLogin(), "some message", Communication.EMAIL);
    }

    @Test
    public void getAllDocsTest() {
        List<BugReportsDTO> bugReportsDTOS = List.of(generateTestBugReportDTO());
        List<Bug> testList = List.of(generateTestBug());

        when(bugReportRepository.findAll()).thenReturn(testList);
        when(bugReportMapper.entityToDTO(testList.iterator().next())).thenReturn(bugReportsDTOS.iterator().next());

        List<BugReportsDTO> bugReportServiceAll = bugReportService.getAll();
        Assertions.assertEquals(bugReportsDTOS, bugReportServiceAll);
    }

    @Test
    public void downloadFileTest() {
        Bug bug = generateTestBug();

        when(bugReportRepository.getById(1L)).thenReturn(bug);

        Bug userBug = bugReportService.downloadFile(1L);

        Assertions.assertEquals(userBug, bug);
    }
}
