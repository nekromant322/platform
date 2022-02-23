package com.override.service;

import org.json.JSONObject;

import java.io.IOException;

public interface LessonStructureService {
    JSONObject scanLessonStructure(String courseName) throws IOException;
}
