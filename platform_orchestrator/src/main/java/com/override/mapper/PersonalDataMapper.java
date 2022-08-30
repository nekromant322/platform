package com.override.mapper;

import com.override.model.PersonalData;
import com.override.model.RequestPersonalData;
import dto.PersonalDataDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonalDataMapper {

    public PersonalData dtoToEntity(PersonalDataDTO personalDataDTO) {

        return PersonalData.builder()
            .id(personalDataDTO.getId())
            .actNumber(personalDataDTO.getActNumber())
            .contractNumber(personalDataDTO.getContractNumber())
            .contractDate(personalDataDTO.getContractDate())
            .fullName(personalDataDTO.getFullName())
            .passportSeries(personalDataDTO.getPassportSeries())
            .passportNumber(personalDataDTO.getPassportNumber())
            .passportIssued(personalDataDTO.getPassportIssued())
            .issueDate(personalDataDTO.getIssueDate())
            .birthDate(personalDataDTO.getBirthDate())
            .registration(personalDataDTO.getRegistration())
            .email(personalDataDTO.getEmail())
            .phoneNumber(personalDataDTO.getPhoneNumber())
            .build();
    }

    public PersonalDataDTO entityToDto(PersonalData personalData) {

        return PersonalDataDTO.builder()
                .id(personalData.getId())
                .actNumber(personalData.getActNumber())
                .contractNumber(personalData.getContractNumber())
                .contractDate(personalData.getContractDate())
                .fullName(personalData.getFullName())
                .passportSeries(personalData.getPassportSeries())
                .passportNumber(personalData.getPassportNumber())
                .passportIssued(personalData.getPassportIssued())
                .issueDate(personalData.getIssueDate())
                .birthDate(personalData.getBirthDate())
                .registration(personalData.getRegistration())
                .email(personalData.getEmail())
                .phoneNumber(personalData.getPhoneNumber())
                .build();
    }

    public RequestPersonalData dataToRequest(PersonalData personalData) {

        return RequestPersonalData.builder()
            .id(personalData.getId())
            .actNumber(personalData.getActNumber())
            .contractNumber(personalData.getContractNumber())
            .contractDate(personalData.getContractDate())
            .fullName(personalData.getFullName())
            .passportSeries(personalData.getPassportSeries())
            .passportNumber(personalData.getPassportNumber())
            .passportIssued(personalData.getPassportIssued())
            .issueDate(personalData.getIssueDate())
            .birthDate(personalData.getBirthDate())
            .registration(personalData.getRegistration())
            .email(personalData.getEmail())
            .phoneNumber(personalData.getPhoneNumber())
            .build();
    }
}
