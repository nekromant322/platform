package com.override.mapper;

import com.override.model.CodeTry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CodeTryMapperTest {
    @InjectMocks
    private CodeTryMapper codeTryMapper;

    @Test
    void testWhenDtoToEntity() {
        final CodeTry codeTry = codeTryMapper.dtoToEntity(generateTestCodeTryDTO(), generateTestTestResultDTO(), generateTestUser());
        final CodeTry testCodeTry = generateTestCodeTry();

        assertEquals(codeTry.getUser(), generateTestUser());
        assertEquals(codeTry.getChapter(), testCodeTry.getChapter());
        assertEquals(codeTry.getStep(), testCodeTry.getStep());
        assertEquals(codeTry.getLesson(), testCodeTry.getLesson());
        assertNotNull(codeTry.getDate());
        assertEquals(codeTry.getStudentsCode(), testCodeTry.getStudentsCode());
        assertEquals(codeTry.getCodeExecutionStatus(), testCodeTry.getCodeExecutionStatus());

    }
}
