package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferDocumentDTO {

    private Long id;
    private String name;
    private String type;

}
