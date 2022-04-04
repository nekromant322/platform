package com.override.mappers;

import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import dtos.PersonalDataDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonalDataMapper {

    public PersonalData dtoToEntity(PersonalDataDTO personalDataDTO, PlatformUser user) {
        return PersonalData.builder()
                .id(personalDataDTO.getId())
                .actNumber(personalDataDTO.getActNumber())
                .contractNumber(personalDataDTO.getContractNumber())
                .date(personalDataDTO.getDate())
                .fullName(personalDataDTO.getFullName())
                .passportSeries(personalDataDTO.getPassportSeries())
                .passportNumber(personalDataDTO.getPassportNumber())
                .passportIssued(personalDataDTO.getPassportIssued())
                .issueDate(personalDataDTO.getIssueDate())
                .birthDate(personalDataDTO.getBirthDate())
                .registration(personalDataDTO.getRegistration())
                .email(personalDataDTO.getEmail())
                .phoneNumber(personalDataDTO.getPhoneNumber())
                .user(user)
                .build();
    }
}
