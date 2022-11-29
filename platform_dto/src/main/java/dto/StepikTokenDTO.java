package dto;

import lombok.Data;

@Data
public class StepikTokenDTO {

    private String access_token;

    private Integer expires_in;

    private String token_type;

    private String scope;
}
