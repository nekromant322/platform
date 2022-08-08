package com.override.utils;

import com.override.exception.UnupdatableDataException;
import com.override.model.PersonalData;
import com.override.util.UnupdatableFieldChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import static org.junit.jupiter.api.Assertions.assertThrows;



@ExtendWith(MockitoExtension.class)
public class CheckerUnupdatableFieldTest {

    @InjectMocks
    private UnupdatableFieldChecker<PersonalData> unupdatableFieldChecker;

//    PersonalData existingPersonalData;
//
//    @BeforeTestMethod
//    public void initPersonalData() {
//
//        existingPersonalData = PersonalData.builder()
//            .actNumber(null)                //@Unupdatable empty field
//            .contractNumber("some number")  //@Unupdatable non-empty field
//            .phoneNumber(111L)              // filed without annotation
//            .build();
//
//    }

    @Test
    public void changingUnupdatableFieldTest() {

        PersonalData existingPersonalData = PersonalData.builder()
                .contractNumber("some number") //@Unupdatable non-empty field
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .contractNumber("some other number")
                .build();

        assertThrows(UnupdatableDataException.class,
                () -> unupdatableFieldChecker.executeCheck(existingPersonalData, newPersonalData));

    }

    @Test
    public void changingUpdatableFieldTest() {

        PersonalData existingPersonalData = PersonalData.builder()
                .phoneNumber(111L) // filed without annotation
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .phoneNumber(333L)
                .build();

        unupdatableFieldChecker.executeCheck(existingPersonalData, newPersonalData);

    }

    @Test
    public void changingUnupdatableEmptyFieldTest() {

        PersonalData existingPersonalData = PersonalData.builder()
                .actNumber(null) //@Unupdatable empty field
                .build();

        PersonalData newPersonalData = PersonalData.builder()
                .actNumber(123L)
                .build();

        unupdatableFieldChecker.executeCheck(existingPersonalData, newPersonalData);

    }
}
