package com.override.service;

import com.override.mappers.CodeTryStatMapper;
import com.override.repositories.CodeTryRepository;
import dtos.CodeTryStatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StatisticsService {
    @Autowired
    private CodeTryRepository codeTryRepository;
    @Autowired
    private CodeTryStatMapper codeTryStatMapper;

    public CodeTryStatDTO getStatistics(int size){
        return codeTryStatMapper.entityToDto(codeTryRepository.countStatsOfHardTasks(size),
                codeTryRepository.countStatsOfUsers(), codeTryRepository.countStatsOfStatus(),
                codeTryRepository.countCodeTryByChapterAndStep());
    }

    public Map<String, Long> countStatsOfHardTasks(int size) {
        return codeTryStatMapper.listToMapHardTask(
                codeTryRepository.countStatsOfHardTasks(size));
    }
}
