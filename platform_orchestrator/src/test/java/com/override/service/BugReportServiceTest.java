package com.override.service;

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

import static com.override.utils.TestFieldsUtil.generateTestBug;
import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BugReportServiceTest {

    @InjectMocks
    private BugReportService bugReportService;

    @Mock
    private BugReportRepository bugReportRepository;

    @Mock
    private PlatformUserService platformUserService;

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
        PlatformUser platformUser = generateTestUser();
        platformUser.setId(1L);

        when(platformUserService.findPlatformUserByLogin("Andrey")).thenReturn(platformUser);

        Bug bug = generateTestBug();
        bug.setUser(platformUser);

        BugReportsDTO bugReportsDTO = BugReportsDTO.builder()
                .id(bug.getId())
                .name(bug.getName())
                .type(bug.getType())
                .text(bug.getText())
                .user(platformUser.getLogin())
                .build();

        List<BugReportsDTO> list = List.of(bugReportsDTO);

        when(bugReportRepository.findAllByUserId(1L)).thenReturn(List.of(bug));

        List<BugReportsDTO> userList = bugReportService.getAll("Andrey");

        Assertions.assertEquals(list, userList);
    }

    @Test
    public void downloadFileTest() {
        Bug bug = generateTestBug();

        when(bugReportRepository.getById(1L)).thenReturn(bug);

        Bug userBug = bugReportService.downloadFile(1L);

        Assertions.assertEquals(userBug, bug);
    }
}
