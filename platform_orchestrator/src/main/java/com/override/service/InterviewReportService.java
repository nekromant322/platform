package com.override.service;

import com.override.mapper.InterviewReportMapper;
import com.override.model.InterviewReport;
import com.override.model.enums.Status;
import com.override.repository.InterviewReportRepository;
import dto.InterviewReportDTO;
import dto.InterviewReportUpdateDTO;
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

    public void update(InterviewReportUpdateDTO interviewReportUpdateDTO, Status status) {
        InterviewReport interviewReport = interviewReportRepository.getById(interviewReportUpdateDTO.getId());
        interviewReport.setStatus(status);
        interviewReport.setMinSalary(interviewReportUpdateDTO.getSalary());
        interviewReport.setMaxSalary(interviewReportUpdateDTO.getSalary());
        interviewReportRepository.save(interviewReport);
    }

    public void delete(Long id) {
        interviewReportRepository.deleteById(id);
    }

    public List<InterviewReportDTO> findAll() {
        return (interviewReportRepository.findAll()).stream()
                .map(interviewReportMapper::entityToDto).collect(Collectors.toList());
    }
}
