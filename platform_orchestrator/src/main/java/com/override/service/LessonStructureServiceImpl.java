package com.override.service;

import com.override.exception.LessonStructureException;
import dtos.LessonStructureDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class LessonStructureServiceImpl implements LessonStructureService {

    @Override
    public LessonStructureDTO scanLessonStructure(String courseName) {
        LessonStructureDTO lessonStructureDTO = new LessonStructureDTO();
        LinkedHashMap<String, LinkedHashMap<String, List<String>>> structureMap = new LinkedHashMap<>();
        LinkedHashMap<String, List<String>> stepLessonStructure = new LinkedHashMap<>();
        List<String> listOfChapters;
        List<String> listOfSteps;
        List<String> listOfLessons;
        String stringPathToCourse = "C:\\Users\\Schneider\\IdeaProjects\\platform\\platform_orchestrator\\src\\main\\resources\\templates\\lessons" + "\\" + courseName;
        listOfChapters = getDirectoryStructure(stringPathToCourse);
        for (String chapter : listOfChapters) {
            listOfSteps = getDirectoryStructure(stringPathToCourse +
                    "\\" + chapter);
            for (String step : listOfSteps) {
                listOfLessons = getDirectoryStructure(stringPathToCourse +
                        "\\" + chapter +
                        "\\" + step);
                stepLessonStructure.put(step, listOfLessons);
            }
            structureMap.put(chapter, stepLessonStructure);
        }
        lessonStructureDTO.setStructureMap(structureMap);
        return lessonStructureDTO; //почему возвращается без уроков? в консоли уроки выводятся
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
        System.out.println(resultList);
        return resultList;
    }
}
