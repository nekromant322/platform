package com.override.service;

import com.override.mappers.InterviewReportMapper;
import com.override.repositories.InterviewReportRepository;
import com.override.repositories.PlatformUserRepository;
import dtos.InterviewReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewReportService {

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private InterviewReportRepository interviewReportRepository;

    @Autowired
    private InterviewReportMapper interviewReportMapper;

    public void saveInterviewReport(InterviewReportDTO interviewReportDTO, String userLogin) {
        interviewReportRepository.save(interviewReportMapper.dtoToEntity(interviewReportDTO,
                platformUserRepository.findFirstByLogin(userLogin)));
    }

    public void deleteInterviewReport(Long id) {
        interviewReportRepository.deleteById(id);
    }

    public List<InterviewReportDTO> findAllInterviewReports() {
        return (interviewReportRepository.findAll()).stream()
                .map(interviewReportMapper::entityToDto).collect(Collectors.toList());
    }
}
