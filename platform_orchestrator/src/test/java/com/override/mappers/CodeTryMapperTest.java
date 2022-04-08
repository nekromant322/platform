package com.override.mappers;

import com.override.models.CodeTry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.*;

@ExtendWith(MockitoExtension.class)
public class CodeTryMapperTest {
    @InjectMocks
    private CodeTryMapper codeTryMapper;

    @Test
    void testDtoToEntity() {

        final CodeTry codeTry = codeTryMapper.dtoToEntity(generateTestCodeTryDTO(), generateTestTestResultDTO(), generateTestUser());
        CodeTry testCodeTry = generateTestCodeTry();

        Assertions.assertEquals(codeTry.getUser(), generateTestUser());
        Assertions.assertEquals(codeTry.getChapter(), testCodeTry.getChapter());
        Assertions.assertEquals(codeTry.getStep(), testCodeTry.getStep());
        Assertions.assertEquals(codeTry.getLesson(), testCodeTry.getLesson());
        Assertions.assertNotNull(codeTry.getDate());
        Assertions.assertEquals(codeTry.getStudentsCode(), generateTestCode());
        Assertions.assertEquals(codeTry.getCodeExecutionStatus(), testCodeTry.getCodeExecutionStatus());
    }
}
