package com.override.service;

import com.override.mapper.PreProjectLessonMapper;
import com.override.repository.PreProjectLessonRepository;
import dto.PreProjectLessonDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreProjectLessonServiceTest {

    @InjectMocks
    PreProjectLessonService preProjectLessonService;

    @Mock
    PreProjectLessonRepository preProjectLessonRepository;

    @Mock
    PlatformUserService platformUserService;

    @Mock
    PreProjectLessonMapper preProjectLessonMapper;

    @Test
    void getAll() {
        preProjectLessonService.getAll();
        verify(preProjectLessonRepository, times(1)).findAll();
    }

    @Test
    void saveLink() {
        when(platformUserService.findPlatformUserByLogin(any())).thenReturn(generateTestUser());
        when(preProjectLessonMapper.dtoToEntity(any())).thenReturn(generateTestPreProjectLesson());

        preProjectLessonService.save(generateTestPreProjectLessonDTO(), generateTestUser().getLogin());

        verify(preProjectLessonRepository, times(1)).save(generateTestPreProjectLesson());
    }

    @Test
    void update() {
        PreProjectLessonDTO preProjectLessonDTO = generateTestPreProjectLessonDTO();
        preProjectLessonDTO.setApprove(true);
        preProjectLessonDTO.setViewed(true);

        when(preProjectLessonMapper.dtoToEntity(any())).thenReturn(generateTestPreProjectLesson());
        when(preProjectLessonRepository.findById(any())).thenReturn(Optional.ofNullable(generateTestPreProjectLesson()));

        preProjectLessonService.update(preProjectLessonDTO);

        verify(preProjectLessonRepository, times(1)).save(generateTestPreProjectLesson());
    }

    @Test
    void getAllByPathName() {

        when(platformUserService.findPlatformUserByLogin(any())).thenReturn(generateTestUser());

        preProjectLessonService.getAllByPathName(generateTestPreProjectLessonDTO(), generateTestUser().getLogin());

        verify(preProjectLessonRepository, times(1)).findAllByTaskIdentifierAndUser(generateTestPreProjectLesson().getTaskIdentifier(), generateTestUser());
    }
}