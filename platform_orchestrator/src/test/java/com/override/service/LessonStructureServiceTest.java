package com.override.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testGetLessonStructure() {
        JsonObject jsonObject = JsonParser.parseString("{\"1\":{\"1\":[\"1.html\",\"2.html\",\"3.html\",\"4.html\"]},\"2\":{\"1\":[\"1.html\",\"2.html\",\"3.html\",\"4.html\"],\"2\":[\"1.html\",\"2.html\"],\"3\":[\"1.html\"],\"4\":[\"1.html\",\"2.html\"]},\"3\":{\"1\":[\"1.html\",\"2.html\",\"3.html\"]}}").getAsJsonObject();
        courseLessonStructure.put("spring", jsonObject);

        lessonStructureService.getLessonStructure("spring");

        verify(courseLessonStructure, times(1)).get(any());
        assertEquals(lessonStructureService.getLessonStructure("spring"), courseLessonStructure.get("spring"));
    }
}