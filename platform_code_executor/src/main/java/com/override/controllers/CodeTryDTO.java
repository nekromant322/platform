package com.override.controllers;

import com.override.model.TaskIdentifier;
import lombok.Data;

@Data
public class CodeTryDTO {

    private TaskIdentifier taskIdentifier;
    private String studentsCode;
}
