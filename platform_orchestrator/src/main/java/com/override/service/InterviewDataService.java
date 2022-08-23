package com.override.service;

import com.override.mapper.InterviewDataMapper;
import com.override.model.InterviewData;
import com.override.repository.InterviewDataRepository;
import dto.InterviewDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewDataService {

    @Autowired
    private InterviewDataRepository interviewDataRepository;

    @Autowired
    private InterviewDataMapper interviewDataMapper;

    public List<InterviewDataDTO> findAll() {
        return (interviewDataRepository.findAll()).stream()
                .map(interviewDataMapper::entityToDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        interviewDataRepository.deleteById(id);
    }

    public void save(InterviewData interviewData) {
        interviewDataRepository.save(interviewData);
    }

    public InterviewData findById(Long id) {
        Optional<InterviewData> interviewData = interviewDataRepository.findById(id);
        return interviewData.get();
    }
}
