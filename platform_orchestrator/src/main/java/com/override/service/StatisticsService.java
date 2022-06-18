package com.override.service;

import com.override.mapper.CodeTryStatMapper;
import com.override.repository.CodeTryRepository;
import dto.CodeTryStatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StatisticsService {
    @Autowired
    private CodeTryRepository codeTryRepository;
    @Autowired
    private CodeTryStatMapper codeTryStatMapper;

    public CodeTryStatDTO getCodeTryStatistics(int size){
        return codeTryStatMapper.entityToDto(codeTryRepository.countStatsOfHardTasks(size),
                codeTryRepository.countStatsOfUsers(), codeTryRepository.countStatsOfStatus(),
                codeTryRepository.countCodeTryByChapterAndStep());
    }

    public Map<String, Long> countStatsOfHardTasks(int size) {
        return codeTryStatMapper.listToMapHardTask(
                codeTryRepository.countStatsOfHardTasks(size));
    }
}
