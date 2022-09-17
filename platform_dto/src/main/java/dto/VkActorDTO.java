package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VkActorDTO {
    private String accessToken;
    private Long expiresIn;
    private Long userId;
}
