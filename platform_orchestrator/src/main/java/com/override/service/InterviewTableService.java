package com.override.service;

import com.override.mapper.InterviewTableMapper;
import com.override.model.InterviewTable;
import com.override.repository.InterviewTableRepository;
import dto.InterviewTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewTableService {

    @Autowired
    private InterviewTableRepository interviewTableRepository;

    @Autowired
    private InterviewTableMapper interviewTableMapper;

    public List<InterviewTableDTO> findAll() {
        return (interviewTableRepository.findAll()).stream()
                .map(interviewTableMapper::entityToDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        interviewTableRepository.deleteById(id);
    }

    public void save(InterviewTable interviewTable) {
        interviewTableRepository.save(interviewTable);
    }

    public InterviewTable findById(Long id) {
        Optional<InterviewTable> interviewTable = interviewTableRepository.findById(id);
        return interviewTable.get();
    }
}
