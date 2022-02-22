package dtos;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class LessonStructureDTO {
    LinkedHashMap<String, LinkedHashMap<String, List<String>>> structureMap;
}
