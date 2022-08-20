package com.override.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequestPersonalData {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "actNumber")
    private Long actNumber;

    @Column(name = "contractNumber")
    private String contractNumber;

    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date contractDate;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "passport_series")
    private Long passportSeries;

    @Column(name = "passport_number")
    private Long passportNumber;

    @Column(name = "passport_issued")
    private String passportIssued;

    @Column(name = "issue_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date issueDate;

    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date birthDate;

    @Column(name = "registration")
    private String registration;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    public RequestPersonalData(PersonalData personalData) {
        this.id = personalData.getId();
        this.actNumber = personalData.getActNumber();
        this.contractNumber = personalData.getContractNumber();
        this.contractDate = personalData.getContractDate();
        this.fullName = personalData.getFullName();
        this.passportSeries = personalData.getPassportSeries();
        this.passportNumber = personalData.getPassportNumber();
        this.passportIssued = personalData.getPassportIssued();
        this.issueDate = personalData.getIssueDate();
        this.birthDate = personalData.getBirthDate();
        this.registration = personalData.getRegistration();
        this.email = personalData.getEmail();
        this.phoneNumber= personalData.getPhoneNumber();
    }

}
