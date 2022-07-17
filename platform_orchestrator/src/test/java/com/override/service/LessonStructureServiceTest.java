package com.override.service;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LessonStructureServiceTest {

    @Mock
    private HashMap<String, JsonObject> courseLessonStructure;

    @InjectMocks
    private LessonStructureService lessonStructureService;

    @Test
    void testGetLessonStructure() {
        lessonStructureService.getLessonStructure(any());

        verify(courseLessonStructure, times(1)).get(any());
    }

/*    need help
  @Test
    void testRefillCourseLessonStructure() throws IOException {
        LessonStructureService spy = spy(new LessonStructureService());

        HashMap<String, JsonObject> courseLessonStructureTest = new HashMap<>();
        courseLessonStructureTest.put("qwe",new JsonObject());
        Stream<Path> fileStream = Stream.of(Path.of("qwe"));//добавить значений

        when(Files.list(any())).thenReturn(fileStream);
        doReturn(new JsonObject()).when(spy,"scanLessonStructure", ArgumentMatchers.anyString());

        lessonStructureService.refillCourseLessonStructure();

        assertEquals(courseLessonStructure,courseLessonStructureTest);
    }*/
}