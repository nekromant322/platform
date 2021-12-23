package dtos;


import enums.VapeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VapeModelDTO {
    private long id;
    private float cost;
    private String img;
    private String description;
    private VapeType type;
}
