package com.override.service;

import com.override.mappers.InterviewReportMapper;
import com.override.models.InterviewReport;
import com.override.models.enums.Status;
import com.override.repositories.InterviewReportRepository;
import dtos.InterviewReportDTO;
import dtos.InterviewReportUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    public void updateOffer(InterviewReportUpdateDTO interviewReportUpdateDTO, Status status, MultipartFile file) {
        InterviewReport interviewReport = interviewReportRepository.getById(interviewReportUpdateDTO.getId());
        interviewReport.setStatus(status);
        interviewReport.setMinSalary(interviewReportUpdateDTO.getSalary());
        interviewReport.setMaxSalary(interviewReportUpdateDTO.getSalary());
        try {
            interviewReport.setFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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


