package com.override.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.override.annotation.Unupdatable;
import io.swagger.annotations.ApiModelProperty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(
            value = "Id персональных данных",
            name = "id",
            dataType = "Long",
            example = "222L")
    private Long id;


    @Column(name = "actNumber")
    @ApiModelProperty(
            value = "Номер акта",
            name = "actNumber",
            dataType = "Long",
            example = "222L")
    private Long actNumber;


    @Column(name = "contractNumber")
    @ApiModelProperty(
            value = "Номер контракта",
            name = "contractNumber",
            dataType = "String",
            example = "22/22/2222")
    private String contractNumber;


    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @ApiModelProperty(
            value = "Дата подписания контракта",
            name = "contractDate",
            dataType = "Date",
            example = "22.22.2222")
    private Date contractDate;


    @Column(name = "full_name")
    @ApiModelProperty(
            value = "Полное имя (ФИО)",
            name = "fullName",
            dataType = "String",
            example = "Ernesto Denesik DDS")
    private String fullName;


    @Column(name = "passport_series")
    @ApiModelProperty(
            value = "Серия паспорта",
            name = "passportSeries",
            dataType = "Long",
            example = "1673")
    private Long passportSeries;


    @Column(name = "passport_number")
    @ApiModelProperty(
            value = "Номер паспорта",
            name = "passportNumber",
            dataType = "Long",
            example = "568174")
    private Long passportNumber;


    @Column(name = "passport_issued")
    @ApiModelProperty(
            value = "Кем паспорт выдан",
            name = "passportIssued",
            dataType = "String",
            example = "3261 Annette Coves, Port Sumiko, IN 66449")
    private String passportIssued;


    @Column(name = "issue_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @ApiModelProperty(
            value = "Годен паспорт до",
            name = "issueDate",
            dataType = "Date",
            example = "22.22.2222")
    private Date issueDate;


    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @ApiModelProperty(
            value = "Дата рождения",
            name = "birthDate",
            dataType = "Date",
            example = "22.22.2222")
    private Date birthDate;

 
    @Column(name = "registration")
    @ApiModelProperty(
            value = "Регистрация",
            name = "registration",
            dataType = "String",
            example = "51981 Schoen Mews, North Stasiabury, DE 83709-7555")
    private String registration;

    @Column(name = "email")
    @ApiModelProperty(
            value = "Почта",
            name = "email",
            dataType = "String",
            example = "chauncey54@gmail.com")
    private String email;

    @Column(name = "phone_number")
    @ApiModelProperty(
            value = "Номер телефона ",
            name = "phoneNumber",
            dataType = "Long",
            example = "81116327532")
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
