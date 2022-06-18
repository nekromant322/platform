package dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class SmsResponseDTO {

    private String status;
    @JsonSetter("status_code")
    private int code;
    private SmsDTO sms;
    private double balance;

}
