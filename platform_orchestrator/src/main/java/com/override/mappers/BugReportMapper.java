package com.override.mappers;

import com.override.models.Bug;
import dtos.BugReportsDTO;
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
