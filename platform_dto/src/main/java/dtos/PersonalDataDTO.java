package dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PersonalDataDTO {
    private long id;
    private Long actNumber;
    private String contractNumber;
    private Date date;
    private String fullName;
    private Long passportSeries;
    private Long passportNumber;
    private String passportIssued;
    private Date issueDate;
    private Date birthDate;
    private String registration;
    private String email;
    private Long phoneNumber;
    private String login;
}
