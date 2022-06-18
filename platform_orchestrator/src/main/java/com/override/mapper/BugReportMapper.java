package com.override.mapper;

import com.override.model.Bug;
import dto.BugReportsDTO;
import org.springframework.stereotype.Component;

@Component
public class BugReportMapper {

    public BugReportsDTO entityToDTO(Bug bug) {
        return BugReportsDTO.builder()
                .id(bug.getId())
                .name(bug.getName())
                .type(bug.getType())
                .text(bug.getText())
                .user(bug.getUser().getLogin())
                .build();
    }
}
