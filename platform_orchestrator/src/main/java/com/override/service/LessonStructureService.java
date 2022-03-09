package com.override.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LessonStructureService {

    private HashMap<String, JsonObject> courseLessonStrucutre;

    public JsonObject getLessonStructure(String courseName) {
        return courseLessonStrucutre.get(courseName);
    }

    @PostConstruct
    public void refillCourseLessonStructure() {
        String stringPathToCourses = "platform_orchestrator" + File.separator +
                                        "src" + File.separator +
                                        "main" + File.separator +
                                        "resources" + File.separator +
                                        "templates" + File.separator +
                                        "lessons";
        List<String> listOfCourses = getDirectoryStructure(stringPathToCourses);
        courseLessonStrucutre = new HashMap<>();
        for (String courseName : listOfCourses) {
            courseLessonStrucutre.put(courseName, scanLessonStructure(courseName));
        }
    }

    private JsonObject scanLessonStructure(String courseName) {
        String stringPathToCourse = "platform_orchestrator" + File.separator +
                                    "src" + File.separator +
                                    "main" + File.separator +
                                    "resources" + File.separator +
                                    "templates" + File.separator +
                                    "lessons" + File.separator +
                                    courseName;
        JsonObject resultJSON = new JsonObject();
        JsonArray lessonsJsonArray;
        JsonObject stepLessonStructure;
        List<String> listOfChapters;
        List<String> listOfSteps;
        List<String> listOfLessons;
        listOfChapters = getDirectoryStructure(stringPathToCourse);
        for (String chapter : listOfChapters) {
            listOfSteps = getDirectoryStructure(stringPathToCourse +
                    File.separator + chapter);
            stepLessonStructure = new JsonObject();
            for (String step : listOfSteps) {
                listOfLessons = getDirectoryStructure(stringPathToCourse +
                        File.separator + chapter +
                        File.separator + step);
                lessonsJsonArray = new JsonArray(listOfLessons.size());
                for (String lesson : listOfLessons) {
                    lessonsJsonArray.add(lesson);
                }
                stepLessonStructure.add(step, lessonsJsonArray);
            }
            resultJSON.add(chapter, stepLessonStructure);
        }
        return resultJSON;
    }

    @SneakyThrows
    private List<String> getDirectoryStructure(String stringPathToDirectory) {
        List<String> resultList;
        Path path = Paths.get(stringPathToDirectory);
        Stream<Path> fileStream = Files.list(path);
        resultList = fileStream
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        return resultList;
    }
}
