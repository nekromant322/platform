package dtos;

import enums.RequestStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseJoinRequestDTO {
    private RequestStatus status;
    private StudentAccountDTO accountDTO;
}
