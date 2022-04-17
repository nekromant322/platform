package com.override.service;

import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LessonProgressServiceTest {

    @InjectMocks
    private LessonProgressService lessonProgressService;

    @Mock
    private PlatformUserService platformUserService;

    @Test
    public void testSaveLessonProgress() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setLessonProgress(new ArrayList<>());

        String lesson = "test-1-1-1";

        lessonProgressService.checkLesson(platformUser, lesson);

        verify(platformUserService, times(1)).update(any());

        List<String> lessons = new ArrayList<>();
        lessons.add(lesson);

        assertEquals(platformUser.getLessonProgress(), lessons);

    }

    @Test
    public void testSaveSameLessonProgress() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setLessonProgress(new ArrayList<>());

        String lesson = "test-1-1-1";

        lessonProgressService.checkLesson(platformUser, lesson);
        lessonProgressService.checkLesson(platformUser, lesson);

        verify(platformUserService, times(1)).update(any());
    }

}
