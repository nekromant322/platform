package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonErrorDTO {

    /**
     * сообщение об ошибке, которое может быть показано на клиенте
     */
    private String message;


    /**
     * время возникновения ошибки
     */
    private Instant timestamp;
}