package com.override.model;


import com.override.annotation.Unupdatable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Unupdatable
    @Column(name = "act_number")
    private Long actNumber;

    @Unupdatable
    @Column(name = "contract_number")
    private String contractNumber;

    @Unupdatable
    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Unupdatable
    @Column(name = "full_name")
    private String fullName;

    @Unupdatable
    @Column(name = "passport_series")
    private Long passportSeries;

    @Unupdatable
    @Column(name = "passport_number")
    private Long passportNumber;

    @Unupdatable
    @Column(name = "passport_issued")
    private String passportIssued;

    @Unupdatable
    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Unupdatable
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Unupdatable
    @Column(name = "registration")
    private String registration;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;
}
