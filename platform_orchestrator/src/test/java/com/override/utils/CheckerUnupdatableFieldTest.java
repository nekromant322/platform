package com.override.utils;

import com.override.exception.UnupdatableDataException;
import com.override.model.PersonalData;
import com.override.util.UnupdatableFieldChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CheckerUnupdatableFieldTest {

    @InjectMocks
    private UnupdatableFieldChecker<PersonalData> checker;

    @Test
    public void changingUnupdatableFieldTest() {

        PersonalData existingPersonalData = PersonalData.builder()
                .contractNumber("some number") //@Unupdatable non-empty field
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .contractNumber("some other number")
                .build();

        assertThrows(UnupdatableDataException.class,
                () -> checker.executeCheckOfFieldChanges(existingPersonalData, newPersonalData));

    }

    @Test
    public void changingUpdatableFieldTest() {

        PersonalData existingPersonalData = PersonalData.builder()
                .phoneNumber(111L) // field without annotation
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .phoneNumber(333L)
                .build();

        checker.executeCheckOfFieldChanges(existingPersonalData, newPersonalData);

    }

    @Test
    public void changingUnupdatableEmptyFieldTest() {

        PersonalData existingPersonalData = PersonalData.builder()
                .actNumber(null) //@Unupdatable empty field
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .actNumber(123L)
                .build();

        checker.executeCheckOfFieldChanges(existingPersonalData, newPersonalData);

    }

    @Test
    public void fillingInEmptyFields() {

        PersonalData existingPersonalData = PersonalData.builder()
                .actNumber(null) //@Unupdatable empty field
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .actNumber(123L)
                .build();

        assertTrue(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData));

    }

    @Test
    public void fillingInNonEmptyFields() {

        PersonalData existingPersonalData = PersonalData.builder()
                .actNumber(1L) //@Unupdatable non empty field
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .actNumber(123L)
                .build();

        assertFalse(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData));

    }

}
