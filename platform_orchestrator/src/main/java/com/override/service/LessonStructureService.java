package com.override.service;

import dtos.LessonStructureDTO;

import java.io.IOException;

public interface LessonStructureService {
    LessonStructureDTO scanLessonStructure(String courseName) throws IOException;
}
