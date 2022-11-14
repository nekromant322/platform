package dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    String username;
    String password;
    String passwordRepeated;
    String code;
}
