package com.override.service;

import com.override.exception.BugReportException;
import com.override.mappers.BugReportMapper;
import com.override.models.Bug;
import com.override.repositories.BugReportRepository;
import dtos.BugReportsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BugReportService {

    @Autowired
    private BugReportRepository bugReportRepository;

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private BugReportMapper bugReportMapper;

    public void uploadFile(MultipartFile file, String login, String text) {

        try {
                bugReportRepository.save(Bug.builder()
                        .content(file.isEmpty() ? null : file.getBytes())
                        .text(text)
                        .type(file.isEmpty() ? "text" : file.getContentType())
                        .name(file.isEmpty() ? "-" : file.getOriginalFilename())
                        .user(platformUserService.findPlatformUserByLogin(login))
                        .build());
        } catch (IOException e) {
            throw new BugReportException("Неверный формат файла");
        }
    }

    public List<BugReportsDTO> getAll() {
        List<Bug> bugs = bugReportRepository.findAll();
        return bugs.stream().map(bugReportMapper::entityToDTO).collect(Collectors.toList());

    }

    public Bug downloadFile(Long fileId) {
        return bugReportRepository.getById(fileId);
    }
}
