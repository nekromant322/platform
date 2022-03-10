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
    CodeTryMapper codeTryMapper;

    @Test
    void testDtoToEntity() {

        final CodeTry codeTry = codeTryMapper.dtoToEntity(generateTestCodeTryDTO(), generateTestTestResultDTO(), generateTestUser());

        Assertions.assertEquals(codeTry.getUser(), generateTestUser());
        Assertions.assertEquals(codeTry.getChapter(), generateTestCodeTry().getChapter());
        Assertions.assertEquals(codeTry.getStep(), generateTestCodeTry().getStep());
        Assertions.assertEquals(codeTry.getLesson(), generateTestCodeTry().getLesson());
        Assertions.assertNotNull(codeTry.getDate());
        Assertions.assertEquals(codeTry.getStudentsCode(), generateTestCode());
        Assertions.assertEquals(codeTry.getCodeExecutionStatus(), generateTestCodeTry().getCodeExecutionStatus());
    }
}
