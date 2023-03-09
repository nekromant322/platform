package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestStatusDTO {
    @Schema(description = "Будет одно из трех сообщений", example = "\"В этом чате уже есть запрос на " +
            "регистрацию\", \"Вы уже зарегистрированы\", \"Ваш запрос на регистрацию в платформе создан, " +
            "ожидайте подтверждения\"")
    private String message;
}
