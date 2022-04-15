package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipientDTO {
    private String login;
    private String email;
    private String phoneNumber;
    private String telegramId;
    private String vkId;
}
