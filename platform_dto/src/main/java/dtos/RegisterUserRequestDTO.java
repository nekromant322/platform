package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserRequestDTO {
    private String telegramUserName;
    private String chatId;
}