package dtos;

import lombok.Data;

@Data
public class CodeCallResponseDTO {
    private String status;

    //Вот здесь нет понимания, лучше String или сразу int?
    private String code;

    private String callID;

    private String cost;

    private String balance;
}
