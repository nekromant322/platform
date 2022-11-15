package dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String username;

    private String password;

    private String code;
}

