package com.override.service;

import com.override.mappers.BugReportMapper;
import com.override.models.Bug;
import com.override.models.PlatformUser;
import com.override.repositories.BugReportRepository;
import dtos.BugReportsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

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

    @Test
    public void uploadFileTest() {
        PlatformUser platformUser = generateTestUser();
        MockMultipartFile file = new MockMultipartFile("bug",
                "Screenshot.png",
                "image/png",
                "some content".getBytes());

        when(platformUserService.findPlatformUserByLogin("Andrey")).thenReturn(platformUser);

        bugReportService.uploadFile(file, platformUser.getLogin(), "some text");
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
