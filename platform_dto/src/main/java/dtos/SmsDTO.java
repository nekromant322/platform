package dtos;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SmsDTO {

    private Map<String, PhoneDTO> phoneDTOMap = new HashMap<>();

    @JsonAnySetter
    public void setPhoneDTOS(String number, PhoneDTO phoneDTO) {
        phoneDTOMap.put(number, phoneDTO);
    }
}
