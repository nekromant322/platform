package com.override.service;

import com.override.repositories.CodeTryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {
    @Autowired
    private CodeTryRepository codeTryRepository;

    public Map<String, Long> countStatsOfHardTasks(int size) {
        Map<String, Long> mapOfHardTasks = new HashMap<>();
        for (Integer[] obj : codeTryRepository.countStatsOfHardTasks(size)) {
            mapOfHardTasks.put(obj[0] + "." + obj[1] + "." + obj[2], (long) obj[3]);
        }
        return mapOfHardTasks;
    }

    public Map<String, BigInteger> countStatsByStatus() {
        Map<String, BigInteger> mapOfStatus = new HashMap<>();
        for(Object[] obj: codeTryRepository.countStatsOfStatus()){
            mapOfStatus.put((String) obj[0], (BigInteger) obj[1]);
        }
        return mapOfStatus;
    }

    public Map<String, BigInteger> countStatsByStatusAndUser() {
        Map<String, BigInteger> mapOfUsers = new HashMap<>();
        for(Object[] obj : codeTryRepository.countStatsOfUsers()){
            mapOfUsers.put((String) obj[0], (BigInteger) obj[1]);
        }
        return mapOfUsers;
    }

    public Map<String, Double> countStatsByChapterAndStepAndStatus() {
        Map<String, Double> mapOfAllTries = new HashMap<>();
        for (Long[] obj : codeTryRepository.countCodeTryByChapterAndStep()) {
            mapOfAllTries.put(obj[0] + "." + obj[1], Double.valueOf(obj[2])/Double.valueOf(obj[3]));
        }
        return mapOfAllTries;
    }
}
