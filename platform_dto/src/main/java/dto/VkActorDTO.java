package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VkActorDTO {

    @ApiModelProperty(
            value = "Токен доступа к VK API",
            example = "vk1.a._4sor6Cg_2YkL3-g5YDaaAHKYSp8NjNDiTCen5mC5XstRpXSn4")
    private String accessToken;

    @ApiModelProperty(
            value = "Время жизни токена 'access token'",
            example = "86400")
    private Long expiresIn;

    @ApiModelProperty(
            value = "ID VK страницы, с которой делается запрос",
            example = "125053L")
    private Long userId;
}
