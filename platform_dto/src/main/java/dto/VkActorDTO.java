package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VkActorDTO {

    @Schema(description = "Токен доступа к VK API",
            example = "vk1.a._4sor6Cg_2YkL3-g5YDaaAHKYSp8NjNDiTCen5mC5XstRpXSn4")
    private String accessToken;

    @Schema(description = "Время жизни токена 'access token'",
            example = "86400")
    private Long expiresIn;

    @Schema(description = "ID VK страницы, с которой делается запрос",
            example = "125053L")
    private Long userId;
}
