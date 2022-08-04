package com.override.utils;

import com.override.model.PersonalData;
import com.override.util.CheckerUnupdatableField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class CheckerUnupdatableFieldTest {

    @Mock
    private CheckerUnupdatableField<PersonalData> checkerUnupdatableField;

    @Test
    public void executeCheck() {

        PersonalData existingPersonalData = new PersonalData();
        PersonalData newPersonalData = new PersonalData();

        checkerUnupdatableField.executeCheck(existingPersonalData, newPersonalData);

        verify(checkerUnupdatableField, times(1)).executeCheck(existingPersonalData, newPersonalData);
    }
}
