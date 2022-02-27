package com.override.service;

import com.google.gson.JsonObject;

import javax.annotation.PostConstruct;
import java.io.IOException;

public interface LessonStructureService {
    JsonObject scanLessonStructure(String courseName) ;
    JsonObject getLessonStructure(String courseName) throws IOException;
}
