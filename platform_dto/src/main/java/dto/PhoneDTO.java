package dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class PhoneDTO {
    private String status;
    @JsonSetter("status_code")
    private int code;
    @JsonSetter("status_text")
    private String statusText;
    @JsonSetter("sms_id")
    private String smsId;
}
