package com.override.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequestPersonalData {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "act_number")
    private Long actNumber;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "passport_series")
    private Long passportSeries;

    @Column(name = "passport_number")
    private Long passportNumber;

    @Column(name = "passport_issued")
    private String passportIssued;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "registration")
    private String registration;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;
}
