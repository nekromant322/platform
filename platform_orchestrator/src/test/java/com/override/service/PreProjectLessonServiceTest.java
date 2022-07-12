package com.override.service;

import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.override.utils.TestFieldsUtil.generatePreProjectLesson;
import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreProjectLessonServiceTest {

    @InjectMocks
    PreProjectLessonService preProjectLessonService;

    @Mock
    PreProjectLessonRepository preProjectLessonRepository;

    @Mock
    PlatformUserService platformUserService;

    @Test
    void getAll() {
        preProjectLessonService.getAll();
        verify(preProjectLessonRepository, times(1)).findAll();
    }

    @Test
    void saveLink() {
        when(platformUserService.findPlatformUserByLogin(any())).thenReturn(generateTestUser());

        preProjectLessonService.saveLink(generatePreProjectLesson(), generateTestUser().getLogin());

        verify(preProjectLessonRepository, times(1)).save(generatePreProjectLesson());
    }

    @Test
    void update() {
        PreProjectLesson preProjectLesson = generatePreProjectLesson();
        preProjectLesson.setApprove(true);
        preProjectLesson.setViewed(true);

        when(preProjectLessonRepository.findById(any())).thenReturn(Optional.ofNullable(generatePreProjectLesson()));

        preProjectLessonService.update(preProjectLesson);

        verify(preProjectLessonRepository, times(1)).save(preProjectLesson);
    }

    @Test
    void getAllByPathName() {

        when(platformUserService.findPlatformUserByLogin(any())).thenReturn(generateTestUser());

        preProjectLessonService.getAllByPathName(generatePreProjectLesson(), generateTestUser().getLogin());

        verify(preProjectLessonRepository, times(1)).findAllByTaskIdentifierAndUser(generatePreProjectLesson().getTaskIdentifier(), generateTestUser());
    }
}