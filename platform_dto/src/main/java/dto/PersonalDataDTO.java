package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PersonalDataDTO {

    @ApiModelProperty(
            value = "Id персональных данных",
            example = "222L")
    private	Long id;

    @ApiModelProperty(
            value = "Номер акта",
            example = "222L")
    private Long actNumber;

    @ApiModelProperty(
            value = "Номер контракта",
            example = "22/22/2222")
    private String contractNumber;

    @ApiModelProperty(
            value = "Дата подписания контракта",
            example = "22.22.2222")
    private LocalDate contractDate;

    @ApiModelProperty(
            value = "Полное имя (ФИО)",
            example = "Ernesto Denesik DDS")
    private String fullName;

    @ApiModelProperty(
            value = "Серия паспорта",
            example = "1673")
    private Long passportSeries;

    @ApiModelProperty(
            value = "Номер паспорта",
            example = "568174")
    private Long passportNumber;

    @ApiModelProperty(
            value = "Кем паспорт выдан",
            example = "3261 Annette Coves, Port Sumiko, IN 66449")
    private String passportIssued;

    @ApiModelProperty(
            value = "Годен паспорт до",
            example = "22.22.2222")
    private LocalDate issueDate;

    @ApiModelProperty(
            value = "Дата рождения",
            example = "22.22.2222")
    private LocalDate birthDate;

    @ApiModelProperty(
            value = "Регистрация",
            example = "51981 Schoen Mews, North Stasiabury, DE 83709-7555")
    private String registration;

    @ApiModelProperty(
            value = "Почта",
            example = "chauncey54@gmail.com")
    private String email;

    @ApiModelProperty(
            value = "Номер телефона ",
            example = "81116327532")
    private Long phoneNumber;
}
