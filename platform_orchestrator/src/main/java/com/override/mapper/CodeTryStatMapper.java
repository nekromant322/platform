package com.override.mapper;

import dto.CodeTryStatDTO;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CodeTryStatMapper {
    public CodeTryStatDTO entityToDto(List<Integer[]> listHardTask, List<Object[]> listStudentsCodeTry,
                                      List<Object[]> listCodeTryStatus, List<Long[]> listStepsCodeTry){

        return CodeTryStatDTO.builder()
                .hardTasks(listToMapHardTask(listHardTask))
                .studentsCodeTry(listToMapStudentCodeTry(listStudentsCodeTry))
                .codeTryStatus(listToMapCodeTryStatus(listCodeTryStatus))
                .hardStepRatio(listToMapAllTriesRatio(listStepsCodeTry))
                .build();
    }

    public Map<String, Long> listToMapHardTask(List<Integer[]> listHardTask) {
        Map<String, Long> mapOfHardTasks = new HashMap<>();
        for (Integer[] obj : listHardTask) {
            mapOfHardTasks.put(obj[0] + "." + obj[1] + "." + obj[2], (long) obj[3]);
        }
        return mapOfHardTasks;
    }

    public Map<String, BigInteger> listToMapStudentCodeTry(List<Object[]> studentsCodeTry){
    Map<String, BigInteger> mapOfStudents = new HashMap<>();
        for(Object[] obj : studentsCodeTry) {
            mapOfStudents.put((String) obj[0], (BigInteger) obj[1]);
        }
        return mapOfStudents;
    }

    public Map<String, BigInteger> listToMapCodeTryStatus(List<Object[]> codeTryStatus) {
        Map<String, BigInteger> mapOfStatus = new HashMap<>();
        for (Object[] obj : codeTryStatus) {
            mapOfStatus.put((String) obj[0], (BigInteger) obj[1]);
        }
        return mapOfStatus;
    }

    public Map<String, Double> listToMapAllTriesRatio(List<Long[]> listStepsCodeTry) {
        Map<String, Double> mapOfAllTriesRatio = new HashMap<>();
        for (Long[] obj : listStepsCodeTry) {
            mapOfAllTriesRatio.put(obj[0] + "." + obj[1], Double.valueOf(obj[2]) / Double.valueOf(obj[3]));
        }
        return mapOfAllTriesRatio;
    }
}
