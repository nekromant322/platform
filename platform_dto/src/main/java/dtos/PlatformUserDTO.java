package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlatformUserDTO {
    private String login;
    private String password;
    private String telegramChatId;
}
