package com.override.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResult {
    private Status status;
    private String output;
}
