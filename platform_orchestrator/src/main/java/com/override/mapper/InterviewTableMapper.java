package com.override.mapper;

import com.override.model.InterviewTable;
import dto.InterviewTableDTO;
import org.springframework.stereotype.Component;

@Component
public class InterviewTableMapper {

    public InterviewTableDTO entityToDto(InterviewTable interviewTable) {
        return InterviewTableDTO.builder()
                .id(interviewTable.getId())
                .userLogin(interviewTable.getUserLogin())
                .company(interviewTable.getCompany())
                .description(interviewTable.getDescription())
                .contacts(interviewTable.getContacts())
                .date(interviewTable.getDate())
                .time(interviewTable.getTime())
                .comment(interviewTable.getComment())
                .stack(interviewTable.getStack())
                .fork(interviewTable.getFork())
                .meetingLink(interviewTable.getMeetingLink())
                .distanceWork(interviewTable.getDistanceWork())
                .build();
    }
}
