package com.override.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordGeneratorServiceTest {

    @InjectMocks
    private PasswordGeneratorService passwordGeneratorService;
}