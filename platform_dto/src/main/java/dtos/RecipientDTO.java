package dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class RecipientDTO {
    private String login;
    @Email
    private String email;
    private String telegramId;
    /**
     * Аннотация @Pattern содержит регулярное выражения для российских номеров
     * формате их записи: +7..., 7..., 8..., 9998886655, 7-999-888-66-55, 7 999 888 66 55
     */
    @Pattern(regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$")
    private String phoneNumber;
}
