package com.override.service;

import com.override.mappers.InterviewReportMapper;
import com.override.repositories.InterviewReportRepository;
import dtos.InterviewReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewReportService {

    @Autowired
    private InterviewReportRepository interviewReportRepository;

    @Autowired
    private InterviewReportMapper interviewReportMapper;

    public void save(InterviewReportDTO interviewReportDTO) {
        interviewReportRepository.save(interviewReportMapper.dtoToEntity(interviewReportDTO));
    }

    public void delete(Long id) {
        interviewReportRepository.deleteById(id);
    }

    public List<InterviewReportDTO> findAll() {
        return (interviewReportRepository.findAll()).stream()
                .map(interviewReportMapper::entityToDto).collect(Collectors.toList());
    }
}
