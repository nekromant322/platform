package com.override.service;

import com.override.repository.PreProjectLessonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class PreProjectLessonServiceTest {

    @InjectMocks
    PreProjectLessonService preProjectLessonService;

    @Mock
    private PreProjectLessonRepository preProjectLessonRepository;

    @Mock
    private PlatformUserService platformUserService;

    @Test
    public void testSaveLink() {
    }
}