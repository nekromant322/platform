package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterStudentRequestDTO {
    String name;
    String chatId;
}