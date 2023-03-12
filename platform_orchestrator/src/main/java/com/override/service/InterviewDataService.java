package com.override.service;

import com.override.mapper.InterviewDataMapper;
import com.override.model.InterviewData;
import com.override.repository.InterviewDataRepository;
import dto.InterviewDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheManager = "getInterviewDataCacheManager")
public class InterviewDataService {

    @Autowired
    private InterviewDataRepository interviewDataRepository;

    @Autowired
    private InterviewDataMapper interviewDataMapper;

    @Cacheable(value = "interviewData")
    public List<InterviewDataDTO> findAll() {
        return (interviewDataRepository.findAll()).stream()
                .map(interviewDataMapper::entityToDto).collect(Collectors.toList());
    }

    @CacheEvict(value = "interviewData", allEntries = true)
    public void delete(Long id) {
        interviewDataRepository.deleteById(id);
    }

    @CacheEvict(value = "interviewData", allEntries = true)
    public void save(InterviewDataDTO interviewDataDTO) {
        interviewDataRepository.save(interviewDataMapper.dtoToEntity(interviewDataDTO));
    }

    public InterviewData findById(Long id) {
        Optional<InterviewData> interviewData = interviewDataRepository.findById(id);
        return interviewData.get();
    }
}