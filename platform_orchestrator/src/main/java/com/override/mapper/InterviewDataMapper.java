package com.override.mapper;

import com.override.model.InterviewData;
import dto.InterviewDataDTO;
import org.springframework.stereotype.Component;

@Component
public class InterviewDataMapper {

    public InterviewDataDTO entityToDto(InterviewData interviewTable) {
        return InterviewDataDTO.builder()
                .id(interviewTable.getId())
                .userLogin(interviewTable.getUserLogin())
                .company(interviewTable.getCompany())
                .description(interviewTable.getDescription())
                .contacts(interviewTable.getContacts())
                .date(interviewTable.getDate())
                .time(interviewTable.getTime())
                .comment(interviewTable.getComment())
                .stack(interviewTable.getStack())
                .salary(interviewTable.getSalary())
                .meetingLink(interviewTable.getMeetingLink())
                .distanceWork(interviewTable.getDistanceWork())
                .build();
    }

    public InterviewData dtoToEntity(InterviewDataDTO interviewTableDTO) {
        return InterviewData.builder()
                .id(interviewTableDTO.getId())
                .userLogin(interviewTableDTO.getUserLogin())
                .company(interviewTableDTO.getCompany())
                .description(interviewTableDTO.getDescription())
                .contacts(interviewTableDTO.getContacts())
                .date(interviewTableDTO.getDate())
                .time(interviewTableDTO.getTime())
                .comment(interviewTableDTO.getComment())
                .stack(interviewTableDTO.getStack())
                .salary(interviewTableDTO.getSalary())
                .meetingLink(interviewTableDTO.getMeetingLink())
                .distanceWork(interviewTableDTO.getDistanceWork())
                .build();
    }
}
