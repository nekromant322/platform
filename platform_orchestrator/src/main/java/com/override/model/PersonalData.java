package com.override.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.override.annotation.Unupdatable;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Unupdatable
    @Column(name = "actNumber")
    private Long actNumber;

    @Unupdatable
    @Column(name = "contractNumber")
    private String contractNumber;

    @Unupdatable
    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;

    @Unupdatable
    @Column(name = "full_name")
    private String fullName;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date issueDate;

    @Unupdatable
    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date birthDate;

    @Unupdatable
@Column(name = "registration")
    private String registration;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

}
