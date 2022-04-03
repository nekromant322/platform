package com.override.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "actNumber")
    private Long actNumber;

    @Column(name = "contractNumber")
    private String contractNumber;

    @Column(name = "date")
    private Date date;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "passportSeries")
    private Long passportSeries;

    @Column(name = "passportNumber")
    private Long passportNumber;

    @Column(name = "passportIssued")
    private String passportIssued;

    @Column(name = "issueDate")
    private Date issueDate;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "registration")
    private String registration;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private Long phoneNumber;

}
