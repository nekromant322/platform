package com.override.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskIdentifier {

    private Integer chapter;
    private Integer step;
    private Integer lesson;
}
