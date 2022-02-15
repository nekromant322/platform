package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentAccountDTO {
    private String login;
    private String password;
    private String telegramChatId;
}
