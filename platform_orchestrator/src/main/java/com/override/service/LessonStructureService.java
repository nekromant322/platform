package com.override.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Component
public class LessonStructureService {

    HashMap<String, JsonObject> courseLessonStrucutre;

    public JsonObject getLessonStructure(String courseName) {
        return courseLessonStrucutre.get(courseName);
    }

    @PostConstruct
    private void postConstruct() {
        String stringPathToCourses = "platform_orchestrator/src/main/resources/templates/lessons"; /*C:\Users\Schneider\IdeaProjects\platform\platform_orchestrator\src\main\resources\templates\lessons\*/
        List<String> listOfCourses = getDirectoryStructure(stringPathToCourses);
        courseLessonStrucutre = new HashMap<>();
        for (String courseName : listOfCourses) {
            courseLessonStrucutre.put(courseName, scanLessonStructure(courseName));
        }
    }

    private JsonObject scanLessonStructure(String courseName) {
        String stringPathToCourse = "platform_orchestrator/src/main/resources/templates/lessons/" + courseName;
        JsonObject resultJSON = new JsonObject();
        JsonArray lessonsJsonArray;
        JsonObject stepLessonStructure;
        List<String> listOfChapters;
        List<String> listOfSteps;
        List<String> listOfLessons;
        listOfChapters = getDirectoryStructure(stringPathToCourse);
        for (String chapter : listOfChapters) {
            listOfSteps = getDirectoryStructure(stringPathToCourse +
                    "\\" + chapter);
            stepLessonStructure = new JsonObject();
            for (String step : listOfSteps) {
                listOfLessons = getDirectoryStructure(stringPathToCourse +
                        "\\" + chapter +
                        "\\" + step);
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
