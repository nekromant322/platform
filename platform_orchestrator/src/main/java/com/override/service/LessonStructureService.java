package com.override.service;

import com.google.gson.JsonObject;

import java.io.IOException;

public interface LessonStructureService {
    JsonObject scanLessonStructure(String courseName) throws IOException;
}
