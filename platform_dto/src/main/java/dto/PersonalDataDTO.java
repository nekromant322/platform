package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PersonalDataDTO {

    @Schema(
            description = "Id персональных данных",
            example = "222L")
    private	Long id;

    @Schema(
            description = "Номер акта",
            example = "222L")
    private Long actNumber;

    @Schema(
            description = "Номер контракта",
            example = "22/22/2222")
    private String contractNumber;

    @Schema(
            description = "Дата подписания контракта",
            example = "22.22.2222")
    private LocalDate contractDate;

    @Schema(
            description = "Полное имя (ФИО)",
            example = "Ernesto Denesik DDS")
    private String fullName;

    @Schema(
            description = "Серия паспорта",
            example = "1673")
    private Long passportSeries;

    @Schema(
            description = "Номер паспорта",
            example = "568174")
    private Long passportNumber;

    @Schema(
            description = "Кем паспорт выдан",
            example = "3261 Annette Coves, Port Sumiko, IN 66449")
    private String passportIssued;

    @Schema(
            description = "Годен паспорт до",
            example = "22.22.2222")
    private LocalDate issueDate;

    @Schema(
            description = "Дата рождения",
            example = "22.22.2222")
    private LocalDate birthDate;

    @Schema(
            description = "Регистрация",
            example = "51981 Schoen Mews, North Stasiabury, DE 83709-7555")
    private String registration;

    @Schema(
            description = "Почта",
            example = "chauncey54@gmail.com")
    private String email;

    @Schema(
            description = "Номер телефона ",
            example = "81116327532")
    private Long phoneNumber;
}
