package com.override.mappers;

import com.override.models.CodeTry;
import com.override.models.PlatformUser;
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
        final PlatformUser user = generateTestUser();
        final CodeTry testCodeTry = generateTestCodeTry();
        final CodeTry codeTry = codeTryMapper.dtoToEntity(generateTestCodeTryDTO(), generateTestTestResultDTO(), user);

        Assertions.assertEquals(codeTry.getUser(), user);
        Assertions.assertEquals(codeTry.getChapter(), testCodeTry.getChapter());
        Assertions.assertEquals(codeTry.getStep(), testCodeTry.getStep());
        Assertions.assertEquals(codeTry.getLesson(), testCodeTry.getLesson());
        Assertions.assertNotNull(codeTry.getDate());
        Assertions.assertEquals(codeTry.getStudentsCode(), testCodeTry.getStudentsCode());
        Assertions.assertEquals(codeTry.getCodeExecutionStatus(), testCodeTry.getCodeExecutionStatus());
    }
}
