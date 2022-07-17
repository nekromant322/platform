package com.override.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordGeneratorServiceTest {

    @InjectMocks
    PasswordGeneratorService passwordGeneratorService;

   /* need help
   @Test
    void testGenerateStrongPassword() {

        final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
        final String DIGIT = "0123456789";
        final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";
        final String OTHER_SYMBOL = "~$^+=<>";
        final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;
        final int PASSWORD_LENGTH = 20;
        final String PASSWORD_ALLOW =
                CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;
        final SecureRandom random = new SecureRandom();

        String strLowerCase = "aa";

        StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

        verify(result, times(1)).append(strLowerCase);
    }*/
}