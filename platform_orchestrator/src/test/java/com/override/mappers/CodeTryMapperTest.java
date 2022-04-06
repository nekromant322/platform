package com.override.mappers;

import com.override.models.CodeTry;
import com.override.models.PlatformUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CodeTryMapperTest {
    @InjectMocks
    CodeTryMapper codeTryMapper;

    @Test
    void testDtoToEntity() {
        final PlatformUser user = generateTestUser();
        final CodeTry testCodeTry = generateTestCodeTry();
        final CodeTry codeTry = codeTryMapper.dtoToEntity(generateTestCodeTryDTO(), generateTestTestResultDTO(), user);

        assertEquals(codeTry.getUser(), user);
        assertEquals(codeTry.getChapter(), testCodeTry.getChapter());
        assertEquals(codeTry.getStep(), testCodeTry.getStep());
        assertEquals(codeTry.getLesson(), testCodeTry.getLesson());
        assertNotNull(codeTry.getDate());
        assertEquals(codeTry.getStudentsCode(), testCodeTry.getStudentsCode());
        assertEquals(codeTry.getCodeExecutionStatus(), testCodeTry.getCodeExecutionStatus());
    }
}
