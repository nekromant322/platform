package com.override.service;

import com.override.mapper.PreProjectLessonMapper;
import com.override.mapper.PreProjectLessonMentorReactionMapper;
import com.override.model.PreProjectLesson;
import com.override.repository.PreProjectLessonRepository;
import dto.PreProjectLessonMentorReactionDTO;
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
    private PreProjectLessonService preProjectLessonService;

    @Mock
    private PreProjectLessonRepository preProjectLessonRepository;

    @Mock
    private PlatformUserService platformUserService;

    @Mock
    private PreProjectLessonMapper preProjectLessonMapper;

    @Mock
    private PreProjectLessonMentorReactionMapper preProjectLessonMentorReactionMapper;

    @Test
    public void testGetAll() {
        preProjectLessonService.getAll();
        verify(preProjectLessonRepository, times(1)).findAll();
    }

    @Test
    public void testSaveLink() {
        PreProjectLesson preProjectLesson = generateTestPreProjectLesson();
        preProjectLesson.setComments(null);

        when(platformUserService.findPlatformUserByLogin(any())).thenReturn(generateTestUser());
        when(preProjectLessonMapper.dtoToEntity(any())).thenReturn(generateTestPreProjectLesson());

        preProjectLessonService.save(generateTestPreProjectLessonDTO(), generateTestUser().getLogin());

        verify(preProjectLessonRepository, times(1)).save(preProjectLesson);
    }

    @Test
    public void testUpdate() {
        PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO = generateTestPreProjectLessonMentorReactionDTO();
        preProjectLessonMentorReactionDTO.setApprove(true);
        preProjectLessonMentorReactionDTO.setViewed(true);

        when(preProjectLessonMentorReactionMapper.dtoToEntity(any())).thenReturn(generateTestPreProjectLesson());
        when(preProjectLessonRepository.findById(any())).thenReturn(Optional.ofNullable(generateTestPreProjectLesson()));

        preProjectLessonService.update(preProjectLessonMentorReactionDTO);

        verify(preProjectLessonRepository, times(1)).save(generateTestPreProjectLesson());
    }

    @Test
    public void testGetAllByPathName() {
        when(platformUserService.findPlatformUserByLogin(any())).thenReturn(generateTestUser());

        preProjectLessonService.getAllByPathName(generateTestPreProjectLessonDTO(), generateTestUser().getLogin());

        verify(preProjectLessonRepository, times(1)).findAllByTaskIdentifierAndUser(generateTestPreProjectLesson().getTaskIdentifier(), generateTestUser());
    }
}