package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    String chatId;
    String message;
    String type;
    String username;
}
