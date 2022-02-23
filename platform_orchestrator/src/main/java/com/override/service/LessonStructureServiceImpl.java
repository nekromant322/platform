package com.override.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.override.exception.LessonStructureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class LessonStructureServiceImpl implements LessonStructureService {

    @Override
    public JsonObject scanLessonStructure(String courseName) {
        String stringPathToCourse = "C:\\Users\\Schneider\\IdeaProjects\\platform\\platform_orchestrator\\src\\main\\resources\\templates\\lessons" + "\\" + courseName;
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


            /*____________________________________________*/
//        for (String chapter : listOfChapters) {
//            listOfSteps = getDirectoryStructure(stringPathToCourse +
//                    "\\" + chapter);
//            for (String step : listOfSteps) {
//                listOfLessons = getDirectoryStructure(stringPathToCourse +
//                        "\\" + chapter +
//                        "\\" + step);
//                stepLessonStructure.put(step, listOfLessons);
//            }
//            resultJSON.put(chapter, stepLessonStructure);
//        }
        System.out.println(resultJSON.toString());
        return resultJSON;
    }

    private List<String> getDirectoryStructure(String stringPathToDirectory) {
        List<String> resultList;
        Path path = Paths.get(stringPathToDirectory);
        Stream<Path> fileStream;
        try {
            fileStream = Files.list(path);
        } catch (IOException e) {
            log.error("Caught exeption on getDirectoryStructure(): ", e);
            throw new LessonStructureException();
        }
        resultList = fileStream
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        return resultList;
    }
}
