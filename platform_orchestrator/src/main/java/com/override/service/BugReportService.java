package com.override.service;

import com.override.models.Bug;
import com.override.repositories.BugReportRepository;
import dtos.BugReportsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BugReportService {

    @Autowired
    private BugReportRepository bugReportRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void uploadFile(MultipartFile file, String login, String text) {

        try {
            Bug bug = new Bug();
            bug.setContent(file.getBytes());
            bug.setText(text);
            bug.setType(file.getContentType());
            bug.setName(file.getOriginalFilename());
            bug.setUser(platformUserService.findPlatformUserByLogin(login));
            bugReportRepository.save(bug);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BugReportsDTO> getAll(String login) {
        List<Bug> bugs = bugReportRepository.findAllByUserId(
                platformUserService.findPlatformUserByLogin(login).getId());
        List<BugReportsDTO> bugReportsDTOS = new ArrayList<>();
        for (Bug bug : bugs) {
            bugReportsDTOS.add(BugReportsDTO.builder()
                    .id(bug.getId())
                    .name(bug.getName())
                    .text(bug.getText())
                    .type(bug.getType())
                    .user(bug.getUser().getLogin())
                    .build());
        }
        return bugReportsDTOS;
    }

    public Bug downloadFile(Long fileId) {
        return bugReportRepository.getById(fileId);
    }
}
