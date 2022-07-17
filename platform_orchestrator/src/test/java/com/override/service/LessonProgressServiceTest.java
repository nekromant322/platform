package com.override.service;

import com.override.model.LessonProgress;
import com.override.model.PlatformUser;
import com.override.repository.LessonProgressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonProgressServiceTest {

    @InjectMocks
    private LessonProgressService lessonProgressService;

    @Mock
    private LessonProgressRepository lessonProgressRepository;

    @Test
    public void testWhenSaveLessonProgress() {
        PlatformUser platformUser = new PlatformUser();

        String lesson = "test-1-1-1";

        lessonProgressService.markLessonAsPassed(platformUser, lesson);

        verify(lessonProgressRepository, times(1)).save(any());
    }

    @Test
    public void testWhenSaveSameLessonProgress() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setId(1L);
        platformUser.setLogin("login");
        LessonProgress lessonProgress = LessonProgress.builder().lesson("test-1-1-1").user(platformUser).build();

        when(lessonProgressRepository.findAllByUserId(1L)).thenReturn(List.of(lessonProgress));

        lessonProgressService.markLessonAsPassed(platformUser, "test-1-1-1");

        verify(lessonProgressRepository, never()).save(any());
    }

    @Test
    public void testWhenGettingLessonProgress() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setId(1L);
        platformUser.setLogin("login");
        LessonProgress lessonProgress1 = LessonProgress.builder().lesson("test-1-1-1").user(platformUser).build();
        LessonProgress lessonProgress2 = LessonProgress.builder().lesson("test-2-2-2").user(platformUser).build();

        when(lessonProgressRepository.findAllByUserId(1L)).thenReturn(List.of(lessonProgress1, lessonProgress2));

        lessonProgressService.markLessonAsPassed(platformUser, "test-1-1-1");
        lessonProgressService.markLessonAsPassed(platformUser, "test-2-2-2");

        List<String> progress = new ArrayList<>();
        progress.add("test-1-1-1");
        progress.add("test-2-2-2");

        List<String> userProgress = lessonProgressService.getPassedLessons(platformUser);

        Assertions.assertEquals(progress, userProgress);
    }
}
